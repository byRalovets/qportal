package by.ralovets.qportal.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class FieldDTO {

    private Integer id;
    private String label;
    private Boolean isActive;
    private Boolean isRequired;
    private String type;
    private List<String> options = new ArrayList<>();

    public FieldDTO() {
    }

    public FieldDTO(Integer id, String label, Boolean isActive, Boolean isRequired, String type, List<String> options) {
        this.id = id;
        this.label = label;
        this.isActive = isActive;
        this.isRequired = isRequired;
        this.type = type;
        this.options = options;
    }
}


