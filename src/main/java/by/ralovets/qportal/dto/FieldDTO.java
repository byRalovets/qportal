package by.ralovets.qportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {

    private Integer id;

    @NotBlank(message = "Label cannot be empty")
    private String label;

    @NotNull(message = "isActive flag cannot be empty")
    private Boolean isActive;

    @NotNull(message = "isRequired flag cannot be empty")
    private Boolean isRequired;

    @NotBlank(message = "Type cannot be empty")
    private String type;

    private List<String> options = new ArrayList<>();
}


