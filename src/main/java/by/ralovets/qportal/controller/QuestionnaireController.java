package by.ralovets.qportal.controller;

import by.ralovets.qportal.domain.dto.FieldDTO;
import by.ralovets.qportal.domain.dto.ResponseDTO;
import by.ralovets.qportal.service.FieldService;
import by.ralovets.qportal.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    @GetMapping
    List<FieldDTO> getQuestionnaire() {
        return questionnaireService.getQuestionnaire();
    }

    @PostMapping
    void newResponse(@RequestBody ResponseDTO responseDTO) {
        questionnaireService.newResponse(responseDTO);
    }

    @GetMapping("/results")
    List<ResponseDTO> getResponses() {
        return questionnaireService.getResponses();
    }
}
