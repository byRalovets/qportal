package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.ResponseDTO;

import java.util.List;

public interface QuestionnaireService {

    void newResponse(ResponseDTO dto);
    List<FieldDTO> getQuestionnaire();

}
