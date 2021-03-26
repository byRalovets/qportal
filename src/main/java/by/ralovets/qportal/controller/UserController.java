package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.UpdatePasswordRequestDTO;
import by.ralovets.qportal.dto.UpdateProfileRequestDTO;
import by.ralovets.qportal.service.UserProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
@AllArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserProfileService userProfileService;

    @PostMapping("update-password")
    public JwtResponseDTO updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequest) {
        return userProfileService.updatePassword(updatePasswordRequest);
    }

    @PostMapping("update-profile")
    public JwtResponseDTO updateProfile(@RequestBody UpdateProfileRequestDTO updateProfileRequest) {
        return userProfileService.updateUser(updateProfileRequest);
    }
}
