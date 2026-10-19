package ekabadi_backend.service;

import org.springframework.stereotype.Service;

import ekabadi_backend.model.User;
import ekabadi_backend.repository.UserRepository;

@Service 
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;


    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    
}
