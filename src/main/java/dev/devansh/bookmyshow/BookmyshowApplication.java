package dev.devansh.bookmyshow;

import dev.devansh.bookmyshow.controllers.UserController;
import dev.devansh.bookmyshow.dtos.SignUpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner {
    @Autowired
    private UserController userController;

    @Override
    public void run(String... args) throws Exception {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setEmail("devansh@google.com");
        signUpRequestDto.setPassword("password");

        userController.signUp(signUpRequestDto);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookmyshowApplication.class, args);

    }

}
