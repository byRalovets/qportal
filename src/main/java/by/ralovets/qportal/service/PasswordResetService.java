package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.ResetPasswordDTO;

public interface PasswordResetService {

    void sendResetEmail(String email);
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
