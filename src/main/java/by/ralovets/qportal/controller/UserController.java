package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.UpdatePasswordRequestDTO;
import by.ralovets.qportal.dto.UpdateProfileRequestDTO;
import by.ralovets.qportal.service.UserProfileService;
import by.ralovets.qportal.service.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
@AllArgsConstructor
@RestController
@Slf4j
public class UserController {

    public static final String MSG_INVALID_PASSWORD = "Failed to update password. Check the sent data.";
    public static final String MSG_INVALID_PROFILE_INFO = "Failed to update profile info. Check the sent data.";

    private final UserProfileService userProfileService;

    @PostMapping("update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequest) {
        try {
            return ResponseEntity.ok(userProfileService.updatePassword(updatePasswordRequest));
        } catch (InvalidArgumentException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body(MSG_INVALID_PASSWORD);
        }
    }

    @PostMapping("update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequestDTO updateProfileRequest) {
        try {
            return ResponseEntity.ok(userProfileService.updateUser(updateProfileRequest));
        } catch (InvalidArgumentException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body(MSG_INVALID_PROFILE_INFO);
        }
    }
}
