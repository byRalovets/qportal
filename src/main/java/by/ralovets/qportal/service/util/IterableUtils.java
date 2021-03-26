package by.ralovets.qportal.service.util;

import by.ralovets.qportal.dto.FieldDTO;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterableUtils {

    public static <T> List<T> asList(Iterable<T> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static <T> Stream<T> asStream(Iterable<T> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false);
    }
}
