package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.domain.dto.FieldDTO;
import by.ralovets.qportal.domain.entity.Field;
import by.ralovets.qportal.domain.entity.Option;
import by.ralovets.qportal.domain.entity.util.FieldType;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.service.FieldService;
import by.ralovets.qportal.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static by.ralovets.qportal.domain.dto.mapper.FieldMapper.*;
import static by.ralovets.qportal.service.util.FieldTypeUtils.*;

@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;

    @Override
    public void createField(FieldDTO fieldDTO) {
        Field field = mapToField(fieldDTO);
        List<Option> options = mapToOptions(fieldDTO);

        fieldRepository.save(field);

        options.forEach(o -> o.setField(field));
        optionRepository.saveAll(options);
    }

    @Transactional
    @Override
    public FieldDTO updateField(FieldDTO fieldDTO, Integer id) {

        Field newField = mapToField(fieldDTO);
        final List<Option> newOptions = Objects
                .requireNonNullElse(mapToOptions(fieldDTO), new ArrayList<>());

        return fieldRepository.findById(id)
                .map(field -> {
                    newOptions.forEach(option -> option.setField(field));

                    FieldType was = field.getType();
                    FieldType become = newField.getType();

                    if (wasTextBecomeOptions(was, become)) {
                        optionRepository.saveAll(newOptions);
                    } else if (wasOptionsBecomeText(was, become)) {
                        optionRepository.removeByFieldId(id);
                    } else if (wasOptionsBecomeOptions(was, become)) {
                        optionRepository.removeByFieldId(id);
                        optionRepository.saveAll(newOptions);
                    }

                    field.setType(newField.getType());
                    field.setLabel(newField.getLabel());
                    field.setIsActive(newField.getIsActive());
                    field.setRequired(newField.getRequired());

                    fieldRepository.save(field);

                    return mapToDTO(field, newOptions);
                })
                .orElseGet(() -> {
                    newField.setId(id);
                    fieldRepository.save(newField);

                    newOptions.forEach(o -> o.setField(newField));
                    optionRepository.saveAll(newOptions);

                    return mapToDTO(newField, newOptions);
                });
    }

    @Override
    public void deleteField(Integer id) {
        fieldRepository.deleteById(id);
    }

    @Override
    public FieldDTO findFieldById(Integer id) throws ServiceException {

        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.valueOf(id)));

        List<Option> options = StreamSupport.stream(optionRepository.findByFieldId(id).spliterator(), false)
                .collect(Collectors.toList());

        return mapToDTO(field, options);
    }

    @Override
    public List<FieldDTO> findAll() {

        List<FieldDTO> fieldDTOList = new ArrayList<>();

        fieldRepository.findAll().forEach(field -> {
            List<Option> options = StreamSupport
                    .stream(optionRepository.findByFieldId(field.getId()).spliterator(), false)
                    .collect(Collectors.toList());

            fieldDTOList.add(mapToDTO(field, options));
        });

        return fieldDTOList;
    }

}
