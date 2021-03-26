package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.exception.ResourceNotFoundException;

public interface ResetPasswordService {

    void sendEmailLink(String email);

    JwtResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws ResourceNotFoundException;
}
