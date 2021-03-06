package by.ralovets.qportal.dto.mapper;

import by.ralovets.qportal.dto.AnswerDTO;
import by.ralovets.qportal.dto.ResponseDTO;
import by.ralovets.qportal.model.Answer;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseMapper {

    public static ResponseDTO mapToDTO(List<Answer> answers) {
        return new ResponseDTO(
                answers.stream()
                        .map(a -> new AnswerDTO(a.getFieldId(), a.getText()))
                        .collect(Collectors.toList())
        );
    }

    public static List<Answer> mapToAnswers(ResponseDTO dto) {
        return dto.getAnswers().stream()
                .map(a -> new Answer(a.getFieldId(), a.getText()))
                .collect(Collectors.toList());
    }
}
