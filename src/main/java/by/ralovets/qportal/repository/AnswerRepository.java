package by.ralovets.qportal.repository;

import by.ralovets.qportal.domain.entity.Answer;
import by.ralovets.qportal.domain.entity.Option;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    Iterable<Answer> findByResponseId(Long id);
}
