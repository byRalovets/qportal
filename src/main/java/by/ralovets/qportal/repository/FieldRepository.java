package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.Field;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * CrudRepository implementation for Field entity.
 *
 * @author Anton Ralovets
 * @version 1.0.0
 */
public interface FieldRepository extends PagingAndSortingRepository<Field, Integer> {
}
