package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldHeaderDTO;
import by.ralovets.qportal.dto.ResponseDTO;
import by.ralovets.qportal.service.FormService;
import by.ralovets.qportal.service.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/questionnaire")
@AllArgsConstructor
@RestController
public class QuestionnaireController {

    private final FormService formService;
    private final ResultService resultService;

    @GetMapping
    public List<FieldDTO> getQuestionnaire() {
        return formService.getFields();
    }

    @PostMapping
    public void newResponse(@RequestBody ResponseDTO responseDTO) {
        formService.addResponse(responseDTO);
    }

    @GetMapping("/headers")
    public List<FieldHeaderDTO> getFieldHeaders() {
        return resultService.getFieldHeaders();
    }
}