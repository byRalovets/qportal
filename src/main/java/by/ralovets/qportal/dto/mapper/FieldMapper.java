package by.ralovets.qportal.dto.mapper;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.model.Field;
import by.ralovets.qportal.model.Option;
import by.ralovets.qportal.model.util.FieldType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FieldMapper {

    public static FieldDTO mapToDTO(Field field, List<Option> options) {

        FieldDTO dto = new FieldDTO();

        dto.setId(field.getId());
        dto.setLabel(field.getLabel());
        dto.setIsActive(field.getIsActive());
        dto.setIsRequired(field.getRequired());
        dto.setType(field.getType().name());

        if (Objects.isNull(options)) {
            dto.setOptions(List.of());
        } else {
            dto.setOptions(options.stream()
                    .map(Option::getText)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Field mapToField(FieldDTO dto) {
        Field field = new Field();

        field.setId(dto.getId());
        field.setLabel(dto.getLabel());
        field.setIsActive(dto.getIsActive());
        field.setRequired(dto.getIsRequired());
        field.setType(FieldType.valueOf(dto.getType()));

        return field;
    }

    public static List<Option> mapToOptions(FieldDTO dto) {
        Field field = mapToField(dto);

        return dto.getOptions().stream().filter(s -> !s.isBlank())
                .map(optionText -> new Option(optionText, field))
                .collect(Collectors.toList());
    }
}