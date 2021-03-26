package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * CrudRepository implementation for Option entity.
 *
 * @author Anton Ralovets
 * @version 1.0.0
 */
public interface OptionRepository extends CrudRepository<Option, Integer> {

    /**
     * Searches options by their fieldId.
     *
     * @param id - field id
     * @return iterable interface of options
     * @see by.ralovets.qportal.model.Field
     */
    Iterable<Option> findByFieldId(Integer id);

    /**
     * Removes options by their fieldId.
     *
     * @param id - field id
     * @see by.ralovets.qportal.model.Field
     */
    void removeByFieldId(Integer id);
}
