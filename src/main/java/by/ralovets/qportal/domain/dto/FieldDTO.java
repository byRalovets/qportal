package by.ralovets.qportal.domain.dto;

import by.ralovets.qportal.domain.entity.Field;
import by.ralovets.qportal.domain.entity.Option;
import lombok.Data;

import java.util.List;

@Data
public class FieldDTO {

    private Field field;
    private List<Option> options;

    public FieldDTO() {
    }

    public FieldDTO(Field field, List<Option> options) {
        this.field = field;
        this.options = options;
    }
}


