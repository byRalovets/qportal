package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldsPageDTO;
import by.ralovets.qportal.exception.InvalidArgumentException;
import by.ralovets.qportal.exception.ResourceNotFoundException;
import by.ralovets.qportal.model.Field;
import by.ralovets.qportal.model.Option;
import by.ralovets.qportal.model.util.FieldType;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.service.FieldService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.ralovets.qportal.dto.mapper.FieldMapper.*;
import static by.ralovets.qportal.service.util.FieldTypeUtils.*;
import static by.ralovets.qportal.service.util.IterableUtils.asList;
import static java.util.Objects.requireNonNullElse;

@Slf4j
@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;

    private static final String MSG_FIELD_NOT_FOUND = "Field not found! Check if the id is correct.";
    private static final String MSG_ERR_PAGE_OR_COUNT = "Page number and fields count must be more than 0!";

    /**
     * Creates field in database.
     *
     * @param fieldDTO dto of the field to create; fieldId is ignored
     * @return dto of the created field
     */
    @Override
    public FieldDTO createField(FieldDTO fieldDTO) {

        Field field = mapToField(fieldDTO);
        fieldRepository.save(field);

        List<Option> options = mapToOptions(fieldDTO);
        options.forEach(o -> o.setField(field));
        optionRepository.saveAll(options);

        return mapToDTO(field, options);
    }

    /**
     * Updates field with specified id, or creates new field with this id.
     *
     * @param fieldDTO DTO of field to update
     * @param id       id of field to update
     * @return updated field DTO
     */
    @Transactional
    @Override
    public FieldDTO updateField(FieldDTO fieldDTO, Integer id) {
        fieldDTO.setId(id);

        Field newField = mapToField(fieldDTO);
        final List<Option> newOptions = requireNonNullElse(mapToOptions(fieldDTO), new ArrayList<>());

        return fieldRepository.findById(fieldDTO.getId())
                .map(field -> {
                    newOptions.forEach(option -> option.setField(field));
                    updateFieldOptions(field.getType(), newField.getType(),
                            field, newOptions);

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

                    return mapToDTO(newField, newOptions);
                });
    }

    /**
     * Deletes field from database, if exists.
     *
     * @param id field id
     */
    @Override
    public void deleteField(Integer id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
        }
    }

    /**
     * Returns one field by its id.
     *
     * @param id field id
     * @return found field
     */
    @Override
    public FieldDTO getOne(Integer id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);

        if (fieldOptional.isEmpty())
            throw new ResourceNotFoundException(MSG_FIELD_NOT_FOUND);

        Field field = fieldOptional.get();
        List<Option> options = asList(optionRepository.findByFieldId(id));

        return mapToDTO(field, options);
    }

    /**
     * Returns the dto of the page with the fields it contains
     *
     * @param page  page number
     * @param count number of fields per page
     * @return DTO of page with fields
     * @see FieldsPageDTO
     */
    @Override
    public FieldsPageDTO getAll(int page, int count) {
        if (page < 1 || count < 1) {
            throw new InvalidArgumentException(MSG_ERR_PAGE_OR_COUNT);
        }

        Page<Field> fieldPage = fieldRepository.findAll(PageRequest.of(page - 1, count));

        FieldsPageDTO fieldsPageDTO = new FieldsPageDTO();

        fieldsPageDTO.setFields(getFields(fieldPage));
        fieldsPageDTO.setTotalPages(fieldPage.getTotalPages());
        fieldsPageDTO.setRequestedPage(page);

        return fieldsPageDTO;
    }

    /**
     * Returns fieldDTO list
     *
     * @param page page with fields
     * @return list of field DTO from this page
     */
    private List<FieldDTO> getFields(Page<Field> page) {
        List<FieldDTO> fieldDTOList = new ArrayList<>();

        page.forEach(field -> {
            List<Option> options = asList(optionRepository.findByFieldId(field.getId()));
            fieldDTOList.add(mapToDTO(field, options));
        });

        return fieldDTOList;
    }

    /**
     * Processes options according to field type.
     * If new field contains options, saves or updates these options.
     * Else removes old options of this field.
     *
     * @param was     old field type
     * @param become  new field type
     * @param field   field entity
     * @param options options list
     * @see by.ralovets.qportal.service.util.FieldTypeUtils
     */
    private void updateFieldOptions(FieldType was, FieldType become, Field field, List<Option> options) {
        if (wasTextBecomeOptions(was, become)) {
            optionRepository.saveAll(options);
        } else if (wasOptionsBecomeText(was, become)) {
            optionRepository.removeByFieldId(field.getId());
        } else if (wasOptionsBecomeOptions(was, become)) {
            optionRepository.removeByFieldId(field.getId());
            optionRepository.saveAll(options);
        }
    }
}
