package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.UpdatePasswordRequestDTO;
import by.ralovets.qportal.dto.UpdateProfileRequestDTO;
import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.service.exception.InvalidArgumentException;

public interface UserService {

    JwtResponseDTO updatePassword(UpdatePasswordRequestDTO updatePasswordRequest) throws InvalidArgumentException;

    JwtResponseDTO updateUser(UpdateProfileRequestDTO updateProfileRequest) throws InvalidArgumentException;
}
