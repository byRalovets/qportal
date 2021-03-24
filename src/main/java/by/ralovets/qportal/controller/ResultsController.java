package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.ResultsPageDTO;
import by.ralovets.qportal.dto.ResultsPageRequestDTO;
import by.ralovets.qportal.service.ResultsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Controller
@Slf4j
public class ResultsController {

    private final ResultsService resultsService;

    @MessageMapping("/responses")
    @SendToUser("/topic/greetings")
    public ResultsPageDTO greeting(ResultsPageRequestDTO request, Principal principal) {
        resultsService.addSubscriber(principal.getName(), request);
        return resultsService.getResponses(request.getPage(), request.getCount());
    }

    @GetMapping("/websocket-token")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String aaa() {
        return UUID.randomUUID().toString();
    }
}