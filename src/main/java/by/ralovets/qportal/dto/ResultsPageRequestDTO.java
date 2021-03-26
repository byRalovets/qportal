package by.ralovets.qportal.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultsPageRequestDTO {

    private Integer page;
    private Integer count;
    private String userIdentifier;

    public ResultsPageRequestDTO() {}

    public ResultsPageRequestDTO(Integer page, Integer count, String userIdentifier) {
        this.page = page;
        this.count = count;
        this.userIdentifier = userIdentifier;
    }
}
