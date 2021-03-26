package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.LoginRequestDTO;
import by.ralovets.qportal.dto.SignupRequestDTO;
import by.ralovets.qportal.exception.InvalidArgumentException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    JwtResponseDTO login(LoginRequestDTO loginRequest);

    JwtResponseDTO signup(SignupRequestDTO signupRequest) throws InvalidArgumentException;
}
