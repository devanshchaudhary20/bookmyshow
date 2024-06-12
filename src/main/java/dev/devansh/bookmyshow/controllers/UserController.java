package dev.devansh.bookmyshow.controllers;

import dev.devansh.bookmyshow.dtos.ResponseStatus;
import dev.devansh.bookmyshow.dtos.SignUpRequestDto;
import dev.devansh.bookmyshow.dtos.SignUpResponseDto;
import dev.devansh.bookmyshow.models.User;
import dev.devansh.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto request) {
        User user;
        SignUpResponseDto response = new SignUpResponseDto();

        try {
            user = userService.signUp(request.getEmail(), request.getPassword());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setUserId(user.getId());
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
