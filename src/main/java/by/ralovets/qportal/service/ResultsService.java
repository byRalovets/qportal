package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.FieldHeaderDTO;
import by.ralovets.qportal.dto.ResultsPageRequestDTO;
import by.ralovets.qportal.dto.ResultsPageDTO;

import java.util.List;

public interface ResultsService {

    ResultsPageDTO getResponses(int page, int count);
    List<FieldHeaderDTO> getFieldHeaders();
    void addSubscriber(String username, ResultsPageRequestDTO request);
    void sendResponses();
}
