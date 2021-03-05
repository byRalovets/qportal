package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.entity.Field;
import by.ralovets.qportal.entity.Option;
import by.ralovets.qportal.entity.util.FieldType;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.service.FieldService;
import by.ralovets.qportal.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static by.ralovets.qportal.service.util.FieldTypeUtils.*;

@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;

    @Override
    public void createField(FieldDTO fieldDTO) {
        Field field = fieldDTO.getField();
        List<Option> options = fieldDTO.getOptions();
        options.forEach(o -> o.setField(field));

        fieldRepository.save(field);
        optionRepository.saveAll(options);
    }

    @Override
    public FieldDTO updateField(FieldDTO fieldDTO, Integer id) {
        FieldDTO resultFieldDTO = new FieldDTO();

        Field newField = fieldDTO.getField();
        List<Option> newOptions = fieldDTO.getOptions();

        if (newOptions != null && newOptions.size() != 0)
            newOptions.forEach(o -> o.setField(newField));

        return fieldRepository.findById(id)
                .map(field -> {
                    field.setLabel(newField.getLabel());
                    field.setIsActive(newField.getIsActive());
                    field.setRequired(newField.getRequired());

                    FieldType was = field.getType();
                    FieldType become = newField.getType();

                    if (wasTextBecomeOptions(was, become)) {
                        if (newOptions != null) {
                            newOptions.forEach(o -> o.setField(field));
                            optionRepository.saveAll(newOptions);
                        }
                    } else if (wasOptionsBecomeText(was, become)) {
                        fieldRepository.findById(id).ifPresent(f -> {
                            optionRepository.findByFieldId(f.getId())
                                    .forEach(option -> optionRepository.deleteById(option.getId()));
                        });
                    } else if (wasOptionsBecomeOptions(was, become)) {
                        fieldRepository.findById(id).ifPresent(f -> {
                            optionRepository.findByFieldId(f.getId())
                                    .forEach(option -> optionRepository.deleteById(option.getId()));
                        });
                        if (newOptions != null && newOptions.size() > 0) {
                            newOptions.forEach(o -> o.setField(field));
                            optionRepository.saveAll(newOptions);
                        }
                    }

                    field.setType(newField.getType());

                    fieldRepository.save(field);

                    resultFieldDTO.setField(field);
                    resultFieldDTO.setOptions(newOptions);

                    return resultFieldDTO;
                })
                .orElseGet(() -> {
                    if (newOptions != null) {
                        newOptions.forEach(o -> o.setField(newField));
                        optionRepository.saveAll(newOptions);
                    }
                    newField.setId(id);

                    fieldRepository.save(newField);

                    resultFieldDTO.setField(newField);
                    resultFieldDTO.setOptions(new ArrayList<>(newField.getOptions()));

                    return resultFieldDTO;
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

        return new FieldDTO(field, options);
    }

    @Override
    public List<FieldDTO> findAll() {

        List<FieldDTO> fieldDTOList = new ArrayList<>();

        fieldRepository.findAll().forEach(field -> {
            List<Option> options = StreamSupport
                    .stream(optionRepository.findByFieldId(field.getId()).spliterator(), false)
                    .collect(Collectors.toList());

            fieldDTOList.add(new FieldDTO(field, options));
        });

        return fieldDTOList;
    }

}
