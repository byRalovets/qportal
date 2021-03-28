package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reset-password")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PasswordResetController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping
    public void newField(@RequestBody String email) {
        resetPasswordService.sendEmailLink(email);
    }

    @PutMapping
    public JwtResponseDTO resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return resetPasswordService.resetPassword(resetPasswordDTO);
    }
}
