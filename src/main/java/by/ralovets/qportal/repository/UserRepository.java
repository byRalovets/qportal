package by.ralovets.qportal.repository;

import by.ralovets.qportal.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);

}