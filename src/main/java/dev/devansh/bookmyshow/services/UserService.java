package dev.devansh.bookmyshow.services;

import dev.devansh.bookmyshow.exceptions.PasswordMismatchException;
import dev.devansh.bookmyshow.exceptions.UserNotFoundException;
import dev.devansh.bookmyshow.exceptions.UserWithEmailAlreadyExistsException;
import dev.devansh.bookmyshow.models.User;
import dev.devansh.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) throws UserNotFoundException, PasswordMismatchException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()){
            throw new UserNotFoundException();
        }

        User user = userOptional.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(password, user.getPassword())){
            return user;
        }

        throw new PasswordMismatchException();
    }

    public User signUp(String email, String password) throws UserWithEmailAlreadyExistsException {
        Optional<User> useroptional = userRepository.findByEmail(email);
        if (useroptional.isPresent()){
            throw new UserWithEmailAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
