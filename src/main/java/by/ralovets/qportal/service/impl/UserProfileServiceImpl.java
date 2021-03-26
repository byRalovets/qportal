package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.UpdatePasswordRequestDTO;
import by.ralovets.qportal.dto.UpdateProfileRequestDTO;
import by.ralovets.qportal.repository.UserRepository;
import by.ralovets.qportal.sequrity.jwt.JwtUtils;
import by.ralovets.qportal.sequrity.service.UserDetailsImpl;
import by.ralovets.qportal.service.MailSenderService;
import by.ralovets.qportal.service.UserProfileService;
import by.ralovets.qportal.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final MailSenderService mailSender;
    private final UserRepository userRepository;

    public static final String MSG_INVALID_PASSWORD = "Failed to update password. Check the sent data.";
    public static final String MSG_INVALID_PROFILE_INFO = "Failed to update profile info. Check the sent data.";

    @Override
    public JwtResponseDTO updatePassword(UpdatePasswordRequestDTO updatePasswordRequest) throws InvalidArgumentException {
        if (isNull(updatePasswordRequest)
                || isNull(updatePasswordRequest.getOldPassword())
                || isNull(updatePasswordRequest.getNewPassword())) {
            throw new InvalidArgumentException(MSG_INVALID_PASSWORD);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getEmail(), updatePasswordRequest.getOldPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userRepository.findByEmail(userDetails.getEmail())
                .ifPresent(user -> {
                    user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
                    userRepository.save(user);
                });

        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getEmail(), updatePasswordRequest.getNewPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        userDetails = (UserDetailsImpl) authentication.getPrincipal();

        mailSender.send(
                userDetails.getEmail(),
                "Password successfully changed!",
                String.format("Hi, %s %s! You've changed your password successfully! Don't forget to pass our questionnaire!",
                        userDetails.getFirstName(), userDetails.getLastName())
        );

        return new JwtResponseDTO(
                jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName()
        );
    }

    @Override
    public JwtResponseDTO updateUser(UpdateProfileRequestDTO updateProfileRequest) throws InvalidArgumentException {
        if (isNull(updateProfileRequest)
                || isNull(updateProfileRequest.getEmail())) {
            throw new InvalidArgumentException(MSG_INVALID_PROFILE_INFO);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        userRepository.findByEmail(userDetails.getEmail())
                .ifPresent(user -> {
                    user.setEmail(updateProfileRequest.getEmail());
                    user.setFirstName(updateProfileRequest.getFirstName());
                    user.setLastName(updateProfileRequest.getLastName());
                    user.setPhoneNumber(updateProfileRequest.getPhoneNumber());
                    userRepository.save(user);
                });

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(updateProfileRequest.getEmail(), updateProfileRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponseDTO(
                jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName()
        );
    }
}
