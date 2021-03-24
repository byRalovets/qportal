package by.ralovets.qportal.service;

import by.ralovets.qportal.service.ResultsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SchedulerService {
    private final ResultsService resultsService;

    @Scheduled(fixedRateString = "8000", initialDelayString = "0")
    public void schedulingTask() {
        resultsService.sendResponses();
    }
}