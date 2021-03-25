package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.ResponseDTO;

import java.util.List;

public interface FormService {
    void addResponse(ResponseDTO dto);

    List<FieldDTO> getFields();
}
