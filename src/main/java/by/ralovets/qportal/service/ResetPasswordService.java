package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.ResetPasswordDTO;

public interface ResetPasswordService {
    void sendEmailLink(String email);
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
