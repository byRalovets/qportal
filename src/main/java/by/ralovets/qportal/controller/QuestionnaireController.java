package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldHeaderDTO;
import by.ralovets.qportal.dto.ResponseDTO;
import by.ralovets.qportal.service.QuestionnaireService;
import by.ralovets.qportal.service.ResultsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/questionnaire")
@AllArgsConstructor
@RestController
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final ResultsService resultsService;

    @GetMapping
    List<FieldDTO> getQuestionnaire() {
        return questionnaireService.getQuestionnaire();
    }

    @PostMapping
    void newResponse(@RequestBody ResponseDTO responseDTO) {
        questionnaireService.newResponse(responseDTO);
    }

    @GetMapping("/headers")
    List<FieldHeaderDTO> getFieldHeaders() {
        return resultsService.getFieldHeaders();
    }
}