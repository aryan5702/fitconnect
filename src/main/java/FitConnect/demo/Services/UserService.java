package FitConnect.demo.Services;

import FitConnect.demo.DAO.IUserRepository;
import FitConnect.demo.Entities.User;
import FitConnect.demo.Exceptions.UserAlreadyExistsException;
import FitConnect.demo.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public User addUser(User user) {
        try{
            if(userRepository.existsById(user.getEmail())) {
                throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists.");
            }
            return userRepository.save(user);
        } catch (UserAlreadyExistsException e) {
            System.err.println("\n\n\n\n\n\n" + e.getMessage() + "\n\n\n\n\n\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\n\n\n\n\nAn unexpected error occurred: " + e.getMessage() + "\n\n\n\n\n");
            throw  e;
        }
    }

    public User getUser(String email) {
        try{
            if(userRepository.existsById(email)) {
                return userRepository.findById(email).get();
            }
            throw new UserNotFoundException("User with email " + email + " does not exists.");
        } catch (UserNotFoundException e) {
            System.err.println("\n\n\n\n\n\n" + e.getMessage() + "\n\n\n\n\n\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\n\n\n\n\nAn unexpected error occurred: " + e.getMessage() + "\n\n\n\n\n");
            throw  e;
        }
    }

    public List<User> getUsers() {
        try{
            return userRepository.findAll();
        } catch (Exception e) {
            System.err.println("\n\n\n\n\nAn unexpected error occurred: " + e.getMessage() + "\n\n\n\n\n");
            throw  e;
        }
    }

    public List<User> getUserByDistance(String email, double longitude, double latitude, double distance) {
        try{
            if(userRepository.existsById(email)) throw new UserNotFoundException("User with email " + email + " does not exists.");
            return userRepository.findUsersByLocation(email, longitude, latitude, distance);
//            List<User> usersWithoutEmail = new ArrayList<>();
//            for(User user : users){
//                if(!user.getEmail().equals(email)) usersWithoutEmail.add(user);
//            }
//            return  usersWithoutEmail;
//            return users;
        } catch (UserNotFoundException e) {
            System.err.println("\n\n\n\n\n\n" + e.getMessage() + "\n\n\n\n\n\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\n\n\n\n\nAn unexpected error occurred: " + e.getMessage() + "\n\n\n\n\n");
            throw  e;
        }
    }

    public User updateUser(User user) {
        try{
            if(userRepository.existsById(user.getEmail())) {
                User currentUser = userRepository.findById(user.getEmail()).get();

                if (user.getFirstName() != null && !user.getFirstName().equals("")) currentUser.setFirstName(user.getFirstName());
                if (user.getLastName() != null && !user.getLastName().equals("")) currentUser.setLastName(user.getLastName());
                if (user.getGender() != null && !user.getGender().equals("")) currentUser.setGender(user.getGender());
                if (user.getBio() != null) currentUser.setBio(user.getBio());
                if (user.getDob() != null) currentUser.setDob(user.getDob());
                if (user.getLocation() != null) currentUser.setLocation(user.getLocation());
                if (user.getProfilePic() != null) currentUser.setProfilePic(user.getProfilePic());
                if (user.getInterests() != null) currentUser.setInterests(user.getInterests());

                return userRepository.save(currentUser);
            }
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exists.");
        } catch (UserNotFoundException e) {
            System.err.println("\n\n\n\n\n\n" + e.getMessage() + "\n\n\n\n\n\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\n\n\n\n\nAn unexpected error occurred: " + e.getMessage() + "\n\n\n\n\n");
            throw  e;
        }
    }

    public String deleteUser(String email) {
        try{
            if(userRepository.existsById(email)) {
                userRepository.deleteById(email);
                return "User with email id " + email + " deleted successfully";
            }
            throw new UserNotFoundException("User with email " + email + " does not exists.");
        } catch (UserNotFoundException e) {
            System.err.println("\n\n\n\n\n\n" + e.getMessage() + "\n\n\n\n\n\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\n\n\n\n\nAn unexpected error occurred: " + e.getMessage() + "\n\n\n\n\n");
            throw  e;
        }
    }
}
