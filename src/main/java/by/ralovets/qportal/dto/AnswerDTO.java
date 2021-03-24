package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class AnswerDTO {

    private Integer fieldId;
    private String text;

    public AnswerDTO() {
    }

    public AnswerDTO(Integer fieldId, String text) {
        this.fieldId = fieldId;
        this.text = text;
    }
}
