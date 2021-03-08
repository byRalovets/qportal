package by.ralovets.qportal.repository;

import by.ralovets.qportal.domain.entity.Field;
import by.ralovets.qportal.domain.entity.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Integer> {

    Iterable<Option> findByFieldId(Integer id);

    Long removeByFieldId(Integer id);
}
