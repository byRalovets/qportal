package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    Iterable<Answer> findByResponseId(Long id);
}
