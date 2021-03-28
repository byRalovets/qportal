package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.LoginRequestDTO;
import by.ralovets.qportal.dto.SignupRequestDTO;
import by.ralovets.qportal.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public JwtResponseDTO authenticateUser(@RequestBody @Valid LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/signup")
    public JwtResponseDTO registerUser(@RequestBody @Valid SignupRequestDTO signupRequest) {
        return authService.signup(signupRequest);
    }
}
