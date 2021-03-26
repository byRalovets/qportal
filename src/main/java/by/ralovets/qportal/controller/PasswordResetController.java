package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    JwtResponseDTO resetPassword(
            @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return resetPasswordService.resetPassword(resetPasswordDTO);
    }
}
