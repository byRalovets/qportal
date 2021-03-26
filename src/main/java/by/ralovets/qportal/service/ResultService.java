package by.ralovets.qportal.service;

import by.ralovets.qportal.dto.FieldHeaderDTO;
import by.ralovets.qportal.dto.ResultsPageDTO;
import by.ralovets.qportal.dto.ResultsPageRequestDTO;

import java.util.List;

public interface ResultService {

    List<FieldHeaderDTO> getFieldHeaders();

    void addSubscriber(String userId, ResultsPageRequestDTO request);

    ResultsPageDTO getResults(Integer page, Integer count);

    void sendResponses();
}
