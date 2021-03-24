package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.service.PasswordResetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reset-password")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping
    void newField(@RequestBody String email) {
        passwordResetService.sendResetEmail(email);
    }

    @PutMapping
    void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        passwordResetService.resetPassword(resetPasswordDTO);
    }
}
