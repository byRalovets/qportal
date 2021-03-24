package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.LoginRequestDTO;
import by.ralovets.qportal.dto.SignupRequestDTO;
import by.ralovets.qportal.service.AuthService;
import by.ralovets.qportal.service.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signupRequest) {
        try {
            return ResponseEntity.ok(authService.signup(signupRequest));
        } catch (InvalidArgumentException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body("");
        }
    }
}
