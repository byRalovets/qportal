package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.UpdatePasswordRequestDTO;
import by.ralovets.qportal.dto.UpdateProfileRequestDTO;
import by.ralovets.qportal.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserProfileService userProfileService;

    @PostMapping("update-password")
    public JwtResponseDTO updatePassword(@RequestBody @Valid UpdatePasswordRequestDTO updatePasswordRequest) {
        return userProfileService.updatePassword(updatePasswordRequest);
    }

    @PostMapping("update-profile")
    public JwtResponseDTO updateProfile(@RequestBody @Valid UpdateProfileRequestDTO updateProfileRequest) {
        return userProfileService.updateUser(updateProfileRequest);
    }
}
