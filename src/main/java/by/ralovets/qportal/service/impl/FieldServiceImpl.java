package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldsPageDTO;
import by.ralovets.qportal.model.Field;
import by.ralovets.qportal.model.Option;
import by.ralovets.qportal.model.util.FieldType;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.service.FieldService;
import by.ralovets.qportal.service.exception.InvalidArgumentException;
import by.ralovets.qportal.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static by.ralovets.qportal.dto.mapper.FieldMapper.*;
import static by.ralovets.qportal.service.util.FieldTypeUtils.*;
import static by.ralovets.qportal.service.util.IterableUtils.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNullElse;

@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private static final Logger logger = LoggerFactory.getLogger(FieldServiceImpl.class);

    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;

    @Override
    public FieldDTO createField(FieldDTO fieldDTO) throws InvalidArgumentException {
        if (isNull(fieldDTO)
                || isNull(fieldDTO.getLabel())
                || isNull(fieldDTO.getType())
                || isNull(fieldDTO.getIsActive())
                || isNull(fieldDTO.getIsRequired())
                || isNull(fieldDTO.getOptions()))
            throw new InvalidArgumentException();

        Field field = mapToField(fieldDTO);
        List<Option> options = mapToOptions(fieldDTO);

        fieldRepository.save(field);

        options.forEach(o -> o.setField(field));
        optionRepository.saveAll(options);

        return mapToDTO(field, options);
    }

    @Transactional
    @Override
    public FieldDTO updateField(FieldDTO fieldDTO, Integer id) throws InvalidArgumentException {

        if (isNull(fieldDTO)
                || isNull(fieldDTO.getLabel())
                || isNull(fieldDTO.getType())
                || isNull(fieldDTO.getIsActive())
                || isNull(fieldDTO.getIsRequired())
                || isNull(fieldDTO.getOptions()))
            throw new InvalidArgumentException();

        logger.info(fieldDTO.toString());

        Field newField = mapToField(fieldDTO);
        final List<Option> newOptions = requireNonNullElse(mapToOptions(fieldDTO), new ArrayList<>());

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
                    fieldRepository.save(newField);

                    newOptions.forEach(o -> o.setField(newField));
                    optionRepository.saveAll(newOptions);

                    FieldDTO dto = mapToDTO(newField, newOptions);
                    logger.info(dto.toString());

                    return dto;
                });
    }

    @Override
    public void deleteField(Integer id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
        }
    }

    @Override
    public FieldDTO getOne(Integer id) throws ResourceNotFoundException {
        Field field = fieldRepository.findById(id).orElse(null);

        if (field == null) throw new ResourceNotFoundException();

        List<Option> options = asList(optionRepository.findByFieldId(id));

        return mapToDTO(field, options);
    }

    @Override
    public FieldsPageDTO getAll(int page, int count) throws InvalidArgumentException {
        if (page < 1 || count < 1) {
            throw new InvalidArgumentException();
        }

        Pageable pageable = PageRequest.of(page - 1, count);

        List<FieldDTO> fieldDTOList = new ArrayList<>();

        Page<Field> fieldPage = fieldRepository.findAll(pageable);

        fieldPage.forEach(field -> {
            List<Option> options = asList(optionRepository.findByFieldId(field.getId()));
            fieldDTOList.add(mapToDTO(field, options));
        });

        FieldsPageDTO fieldsPageDTO = new FieldsPageDTO();
        fieldsPageDTO.setTotalPages(fieldPage.getTotalPages());
        fieldsPageDTO.setRequestedPage(page);

        if (fieldDTOList.size() > 0) {
            fieldsPageDTO.setFields(fieldDTOList);
        }

        return fieldsPageDTO;
    }

}
