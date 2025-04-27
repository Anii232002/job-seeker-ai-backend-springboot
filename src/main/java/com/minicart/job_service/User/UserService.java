package com.minicart.job_service.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public void addUser(User user) {
        this.userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isEmpty()){
            throw new IllegalStateException("User not found");
        }
        this.userRepository.deleteById(userId);
    }
}
