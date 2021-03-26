package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.ResultsPageDTO;
import by.ralovets.qportal.dto.ResultsPageRequestDTO;
import by.ralovets.qportal.service.ResultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Controller
@Slf4j
public class ResultsController {

    private final ResultService resultService;

    @MessageMapping("/responses")
    @SendToUser("/topic/greetings")
    public ResultsPageDTO greeting(ResultsPageRequestDTO request, Principal principal) {
        resultService.addSubscriber(principal.getName(), request);
        return resultService.getResults(request.getPage(), request.getCount());
    }
}