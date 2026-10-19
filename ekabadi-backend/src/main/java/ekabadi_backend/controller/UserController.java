package ekabadi_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ekabadi_backend.model.User;
import ekabadi_backend.service.UserService;


@RestController 
@RequestMapping ("/api/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping 
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
    
}
