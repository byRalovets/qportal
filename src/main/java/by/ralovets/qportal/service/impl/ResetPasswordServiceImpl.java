package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.JwtResponseDTO;
import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.exception.ResourceNotFoundException;
import by.ralovets.qportal.model.User;
import by.ralovets.qportal.repository.UserRepository;
import by.ralovets.qportal.sequrity.jwt.JwtUtils;
import by.ralovets.qportal.sequrity.service.UserDetailsImpl;
import by.ralovets.qportal.service.MailSenderService;
import by.ralovets.qportal.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final MailSenderService mailSender;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    private final String resetPasswordPath = "https://qptl.herokuapp.com/reset-password?token=%s";

    private static final String MSG_SUBJECT = "Reset your password";
    private static final String MSG_TEXT_PATTERN = "To reset your password click this link:\n%s";
    public static final String MSG_INVALID_RESET_TOKEN = "Token does't exist. Try again";

    @Override
    public void sendEmailLink(String email) {
        if (!userRepository.existsByEmail(email)) return;

        userRepository.findByEmail(email).ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            String link = String.format(resetPasswordPath, token);

            user.setResetPasswordToken(token);
            userRepository.save(user);

            mailSender.send(email, MSG_SUBJECT, String.format(MSG_TEXT_PATTERN, link));
        });
    }

    @Override
    public JwtResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findByResetPasswordToken(resetPasswordDTO.getToken());

        if (userOptional.isEmpty())
            throw new ResourceNotFoundException(MSG_INVALID_RESET_TOKEN);

        User user = userOptional.get();

        user.setPassword(encoder.encode(resetPasswordDTO.getPassword()));
        user.setResetPasswordToken(null);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), resetPasswordDTO.getPassword()));

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
}
