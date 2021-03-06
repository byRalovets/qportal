package by.ralovets.qportal.service;

import by.ralovets.qportal.domain.dto.FieldDTO;
import by.ralovets.qportal.service.exception.ServiceException;

import java.util.List;

public interface FieldService {

    void createField(FieldDTO field);
    FieldDTO findFieldById(Integer id) throws ServiceException;
    void deleteField(Integer id);
    FieldDTO updateField(FieldDTO field, Integer id);
    List<FieldDTO> findAll();

}
