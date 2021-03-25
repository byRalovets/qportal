package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reset-password")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PasswordResetController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping
    void newField(@RequestBody String email) {
        resetPasswordService.sendEmailLink(email);
    }

    @PutMapping
    void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        resetPasswordService.resetPassword(resetPasswordDTO);
    }
}
