package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class FieldHeaderDTO {

    private Integer fieldId;
    private String label;

    public FieldHeaderDTO() {
    }

    public FieldHeaderDTO(Integer fieldId, String label) {
        this.fieldId = fieldId;
        this.label = label;
    }
}
