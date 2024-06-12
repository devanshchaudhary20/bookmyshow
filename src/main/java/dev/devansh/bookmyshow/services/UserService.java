package dev.devansh.bookmyshow.services;

import dev.devansh.bookmyshow.exceptions.UserWithEmailAlreadyExistsException;
import dev.devansh.bookmyshow.models.User;
import dev.devansh.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String email, String password) throws UserWithEmailAlreadyExistsException {
        Optional<User> useroptional = userRepository.findByEmail(email);
        if (useroptional.isPresent()){
            throw new UserWithEmailAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
