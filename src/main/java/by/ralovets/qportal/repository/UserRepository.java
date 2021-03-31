package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByResetPasswordToken(String resetPasswordToken);
}
