package FitConnect.demo.Controllers;

import FitConnect.demo.Entities.User;
import FitConnect.demo.Exceptions.UserAlreadyExistsException;
import FitConnect.demo.Exceptions.UserNotFoundException;
import FitConnect.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> AddUser(@RequestBody User user) {
        try{
            userService.addUser(user);
            return ResponseEntity.ok("User added successfully!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return  ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> FindUser(@PathVariable String email) {
        try {
            User user = userService.getUser(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body((e.getMessage()));
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> FindAllUser() {
        try {
            List<User> users = userService.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body((e.getMessage()));
        }
    }

    @GetMapping("/usersNearMe")
    public ResponseEntity<?> FindUsersNearMe(
            @RequestParam String email,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {
        try {
            List<User> users = userService.getUserByDistance(email, longitude, latitude, distance);
            return ResponseEntity.ok(users);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateUser")
    public  ResponseEntity<?> UpdateUser (@RequestBody User user){
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{email}")
    public  ResponseEntity<String> DeleteUser(@PathVariable String email) {
        try {
            String successfulDeleteMessage = userService.deleteUser(email);
            return ResponseEntity.ok(successfulDeleteMessage);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
