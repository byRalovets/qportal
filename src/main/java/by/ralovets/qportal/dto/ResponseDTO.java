package by.ralovets.qportal.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private List<AnswerDTO> answers;

    public ResponseDTO() {
    }

    public ResponseDTO(List<AnswerDTO> answers) {
        this.answers = answers;
    }

}

