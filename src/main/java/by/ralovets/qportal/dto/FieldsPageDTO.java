package by.ralovets.qportal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldsPageDTO {

    private List<FieldDTO> fields;
    private Integer totalPages;
    private Integer requestedPage;

    public FieldsPageDTO() {
    }

    public FieldsPageDTO(List<FieldDTO> fields, Integer totalPages, Integer requestedPage) {
        this.fields = fields;
        this.totalPages = totalPages;
        this.requestedPage = requestedPage;
    }
}
