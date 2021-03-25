package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.ResetPasswordDTO;
import by.ralovets.qportal.repository.UserRepository;
import by.ralovets.qportal.sequrity.jwt.JwtUtils;
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

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final MailSenderService mailSender;
    private final String baseLink = "http://localhost:4200/reset-password?token=";
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Override
    public void sendEmailLink(String email) {
        if (!userRepository.existsByEmail(email)) return;

        String token = UUID.randomUUID().toString();

        userRepository.findByEmail(email).ifPresent(user -> {
            user.setResetPasswordToken(token);
            userRepository.save(user);
            String link = baseLink + token;
            mailSender.send(email,
                    "Reset your password",
                    String.format("To reset your password click this link:\n%s", link));
        });
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        userRepository.findByResetPasswordToken(resetPasswordDTO.getToken()).ifPresent(user -> userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    u.setPassword(encoder.encode(resetPasswordDTO.getPassword()));
                    u.setResetPasswordToken(null);
                    userRepository.save(user);

                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getEmail(), resetPasswordDTO.getPassword()));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }));
    }
}
