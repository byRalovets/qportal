package by.ralovets.qportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultsPageDTO {

    private List<ResponseDTO> responses;
    private Integer totalPages;
    private Long totalResponses;
    private Integer requestedPage;
}
