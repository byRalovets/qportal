package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldsPageDTO;
import by.ralovets.qportal.service.exception.InvalidArgumentException;
import by.ralovets.qportal.service.exception.ResourceNotFoundException;

public interface FieldService {
    FieldDTO createField(FieldDTO field) throws InvalidArgumentException;

    FieldDTO updateField(FieldDTO field, Integer id) throws InvalidArgumentException;

    FieldDTO getOne(Integer id) throws ResourceNotFoundException;

    FieldsPageDTO getAll(int page, int count) throws InvalidArgumentException;

    void deleteField(Integer id);
}
