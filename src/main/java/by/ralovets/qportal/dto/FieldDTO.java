package by.ralovets.qportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {

    private Integer id;
    private String label;
    private Boolean isActive;
    private Boolean isRequired;
    private String type;
    private List<String> options = new ArrayList<>();
}


