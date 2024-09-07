package FitConnect.demo.Services;

import FitConnect.demo.DAO.IUserRepository;
import FitConnect.demo.Entities.User;
import FitConnect.demo.Exceptions.UserAlreadyExistsException;
import FitConnect.demo.Exceptions.UserNotFoundException;
import FitConnect.demo.POJO.Interest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> addUser(User user) {
        return userRepository.existsById(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException("User already exists!"));
                    }
                    return userRepository.save(user);
                });
    }

    public Mono<User> getUser(String email) {
        return userRepository.findById(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found!")));
    }

    public Flux<User> getUsers() {
        return userRepository.findAll();
    }

    public Flux<User> getUserByDistance(String email, double longitude, double latitude, double distance) {
        return userRepository.findUsersByLocation(email, longitude, latitude, distance)
                .switchIfEmpty(Flux.error(new UserNotFoundException("No users found near this location!")));
    }

    public Flux<User> findUsersByInterest(User user, Interest interest, String gender, Integer minAge, Integer maxAge, double distance) {
        return userRepository.findUsersByCriteria(user.getEmail(), user.getLocation().getLongitude(), user.getLocation().getLatitude(), distance, gender, minAge, maxAge, interest.getInterest(), interest.getLevel())
                .switchIfEmpty(Flux.error(new UserNotFoundException("No users found with the given interest and criteria!")));
    }

    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.getEmail())
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found!")))
                .flatMap(existingUser -> {

                    if (user.getFirstName() != null && !user.getFirstName().equals("")) existingUser.setFirstName(user.getFirstName());
                    if (user.getLastName() != null && !user.getLastName().equals("")) existingUser.setLastName(user.getLastName());
                    if (user.getGender() != null && !user.getGender().equals("")) existingUser.setGender(user.getGender());
                    if (user.getBio() != null) existingUser.setBio(user.getBio());
                    if (user.getDob() != null) existingUser.setDob(user.getDob());
                    if (user.getLocation() != null) existingUser.setLocation(user.getLocation());
                    if (user.getProfilePic() != null) existingUser.setProfilePic(user.getProfilePic());
                    if (user.getInterests() != null) existingUser.setInterests(user.getInterests());
                    if (user.getPassword() != null) existingUser.setPassword(user.getPassword());

                    return userRepository.save(existingUser);
                });
    }

    public Mono<String> deleteUser(String email) {
        return userRepository.findById(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found!")))
                .flatMap(existingUser -> userRepository.delete(existingUser).then(Mono.just("User deleted successfully!")));
    }

    public Mono<Boolean> login(String email, String password) {
        return userRepository.findById(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found!")))
                .map(user -> user.getPassword().equals(password));
    }
}
