package by.ralovets.qportal.repository;

import by.ralovets.qportal.domain.entity.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Integer> {

    Iterable<Option> findByFieldId(Integer id);
}