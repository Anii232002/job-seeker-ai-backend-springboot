package com.minicart.job_service.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService ;
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return this.userService.getUsers();
    }

    @DeleteMapping(path = "{userId}")
    public void getUsers(Long userId){ this.userService.deleteUser(userId);}

    @PostMapping
    public void addUser(@RequestBody User user) {
        this.userService.addUser(user);
    }
}
