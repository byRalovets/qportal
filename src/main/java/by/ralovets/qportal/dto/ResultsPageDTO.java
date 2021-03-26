package by.ralovets.qportal.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultsPageDTO {

    private List<ResponseDTO> responses;
    private Integer totalPages;
    private Long totalResponses;
    private Integer requestedPage;

    public ResultsPageDTO() {
    }

    public ResultsPageDTO(List<ResponseDTO> responses, Integer totalPages, Long totalResponses, Integer requestedPage) {
        this.responses = responses;
        this.totalPages = totalPages;
        this.totalResponses = totalResponses;
        this.requestedPage = requestedPage;
    }
}
