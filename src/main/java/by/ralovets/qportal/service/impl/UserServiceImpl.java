package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.UpdatePasswordRequestDTO;
import by.ralovets.qportal.dto.UpdateProfileRequestDTO;
import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.repository.UserRepository;
import by.ralovets.qportal.sequrity.jwt.JwtUtils;
import by.ralovets.qportal.sequrity.service.UserDetailsImpl;
import by.ralovets.qportal.service.MailSenderService;
import by.ralovets.qportal.service.UserService;
import by.ralovets.qportal.service.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final MailSenderService mailSender;

    @Override
    public JwtResponseDTO updatePassword(UpdatePasswordRequestDTO updatePasswordRequest) throws InvalidArgumentException {
        if (isNull(updatePasswordRequest)
                || isNull(updatePasswordRequest.getOldPassword())
                || isNull(updatePasswordRequest.getNewPassword())) {
            throw new InvalidArgumentException();
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
            throw new InvalidArgumentException();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        JwtResponseDTO jwtResponse = new JwtResponseDTO();

        userRepository.findByEmail(userDetails.getEmail())
                .ifPresent(user -> {
                    user.setEmail(updateProfileRequest.getEmail());
                    user.setFirstName(updateProfileRequest.getFirstName());
                    user.setLastName(updateProfileRequest.getLastName());
                    user.setPhoneNumber(updateProfileRequest.getPhoneNumber());
                    userRepository.save(user);

                    jwtResponse.setEmail(updateProfileRequest.getEmail());
                    jwtResponse.setFirstName(updateProfileRequest.getFirstName());
                    jwtResponse.setLastName(updateProfileRequest.getLastName());
                });

        String jwt = jwtUtils.generateJwtToken(SecurityContextHolder
                .getContext().getAuthentication());

        userDetails = (UserDetailsImpl) (SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());

        jwtResponse.setToken(jwt);
        jwtResponse.setId(userDetails.getId());

        return jwtResponse;
    }
}
