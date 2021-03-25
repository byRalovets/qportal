package by.ralovets.qportal.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SchedulerService {
    private final ResultService resultService;

    @Scheduled(fixedRateString = "8000", initialDelayString = "0")
    public void schedulingTask() {
        resultService.sendResponses();
    }
}