package by.ralovets.qportal.repository;

import by.ralovets.qportal.model.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OptionRepository extends CrudRepository<Option, Integer> {

    Iterable<Option> findByFieldId(Integer id);

    void removeByFieldId(Integer id);
}
