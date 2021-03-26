package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.LoginRequestDTO;
import by.ralovets.qportal.dto.SignupRequestDTO;
import by.ralovets.qportal.exception.InvalidArgumentException;
import by.ralovets.qportal.exception.ResourceNotFoundException;
import by.ralovets.qportal.model.User;
import by.ralovets.qportal.repository.UserRepository;
import by.ralovets.qportal.sequrity.jwt.JwtUtils;
import by.ralovets.qportal.sequrity.service.UserDetailsImpl;
import by.ralovets.qportal.service.AuthService;
import by.ralovets.qportal.service.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final MailSenderService mailSender;

    private static final String MSG_USER_NOT_FOUND = "User with this email doesn't exist!";
    private static final String MSG_USER_ALREADY_EXISTS = "Email is already in use!";

    @Override
    public JwtResponseDTO login(LoginRequestDTO loginRequest) {
        if (!userRepository.existsByEmail(loginRequest.getEmail())) {
            throw new ResourceNotFoundException(MSG_USER_NOT_FOUND);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponseDTO(
                jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName()
        );
    }

    @Override
    public JwtResponseDTO signup(SignupRequestDTO signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidArgumentException(MSG_USER_ALREADY_EXISTS);
        }

        User user = new User(null, signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()), signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getPhoneNumber());
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        mailSender.send(
                signupRequest.getEmail(),
                "Registration completed!",
                String.format("Hi, %s %s! Welcome to our portal! Don't forget to pass our questionnaire!",
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
}
