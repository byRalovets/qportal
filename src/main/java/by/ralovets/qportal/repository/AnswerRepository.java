package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.Answer;
import org.springframework.data.repository.CrudRepository;

/**
 * CrudRepository implementation for Answer entity.
 *
 * @author Anton Ralovets
 * @version 1.0.0
 */
public interface AnswerRepository extends CrudRepository<Answer, Long> {

    /**
     * Searches answers by their responseId.
     *
     * @param id - response id
     * @return iterable interface of answers
     * @see by.ralovets.qportal.model.Response
     */
    Iterable<Answer> findByResponseId(Long id);
}
