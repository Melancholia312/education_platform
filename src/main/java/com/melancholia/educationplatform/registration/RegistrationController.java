package com.melancholia.educationplatform.registration;

import com.melancholia.educationplatform.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String register() {
        return register(new User("username", "melancholia200312@gmail.com", "123", "+79603566081"));
    }

    @PostMapping
    public String register(User user) {
        return registrationService.register(user);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
