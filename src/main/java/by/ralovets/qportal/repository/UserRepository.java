package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CrudRepository implementation for User entity.
 *
 * @author Anton Ralovets
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Searches for a user by email.
     *
     * @param email - user email
     * @return returns Optional<User> type
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if the user exists by email.
     *
     * @param email - user email
     * @return returns true if user exists by email
     */
    Boolean existsByEmail(String email);

    Optional<User> findByResetPasswordToken(String resetPasswordToken);
}
