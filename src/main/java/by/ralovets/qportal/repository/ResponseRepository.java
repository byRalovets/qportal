package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.Response;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * CrudRepository implementation for Response entity.
 *
 * @author Anton Ralovets
 * @version 1.0.0
 */
public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {
}
