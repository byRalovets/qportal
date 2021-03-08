package by.ralovets.qportal.service;

import by.ralovets.qportal.domain.dto.FieldDTO;
import by.ralovets.qportal.domain.dto.ResponseDTO;

import java.util.List;

public interface QuestionnaireService {

    void newResponse(ResponseDTO dto);
    List<ResponseDTO> getResponses();
    List<FieldDTO> getQuestionnaire();
}
