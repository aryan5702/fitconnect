package FitConnect.demo.Controllers;

import FitConnect.demo.Entities.User;
import FitConnect.demo.Entities.UserRequest;
import FitConnect.demo.Exceptions.UserAlreadyExistsException;
import FitConnect.demo.Exceptions.UserNotFoundException;
import FitConnect.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public Mono<ResponseEntity<String>> addUser(@RequestBody User user) {
        return userService.addUser(user)
                .map(addedUser -> ResponseEntity.ok("User added successfully!"))
                .onErrorResume(UserAlreadyExistsException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage())))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage())));
    }

    @GetMapping("/{email}")
    public Mono<ResponseEntity<User>> findUser(@PathVariable String email) {
        return userService.getUser(email)
                .map(ResponseEntity::ok)
                .onErrorResume(UserNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }

    @GetMapping("/allUsers")
    public Flux<User> findAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/usersNearMe")
    public Flux<User> findUsersNearMe(
            @RequestParam String email,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {
        return userService.getUserByDistance(email, longitude, latitude, distance)
                .onErrorResume(UserNotFoundException.class, e -> Flux.error(new UserNotFoundException(e.getMessage())))
                .onErrorResume(e -> Flux.error(new RuntimeException("Unexpected error occurred")));
    }

    @PostMapping("/findUserByInterest")
    public Flux<User> findUserByInterest(@RequestBody UserRequest request) {
        return userService.findUsersByInterest(
                        request.getUser(),
                        request.getInterest(),
                        request.getGender(),
                        request.getMinAge(),
                        request.getMaxAge(),
                        request.getDistance()
                ).onErrorResume(UserNotFoundException.class, e -> Flux.error(new UserNotFoundException(e.getMessage())))
                .onErrorResume(e -> Flux.error(new RuntimeException("Unexpected error occurred")));
    }

    @PutMapping("/updateUser")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody User user) {
        return userService.updateUser(user)
                .map(ResponseEntity::ok)
                .onErrorResume(UserNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }

    @DeleteMapping("/deleteUser/{email}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable String email) {
        return userService.deleteUser(email)
                .map(ResponseEntity::ok)
                .onErrorResume(UserNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage())));
    }

    @GetMapping("/login/{email}/{password}")
    public Mono<ResponseEntity<Boolean>> login(@PathVariable String email, @PathVariable String password) {
        return userService.login(email, password)
                .map(ResponseEntity::ok)
                .onErrorResume(UserNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false)));
    }
}
