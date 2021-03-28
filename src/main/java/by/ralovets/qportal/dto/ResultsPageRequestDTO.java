package by.ralovets.qportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultsPageRequestDTO {

    @Min(value = 1, message = "Page value must be positive")
    private Integer page;

    @Min(value = 1, message = "Count value must be positive")
    private Integer count;

    @NotEmpty(message = "The user id must not be empty")
    private String userIdentifier;
}
