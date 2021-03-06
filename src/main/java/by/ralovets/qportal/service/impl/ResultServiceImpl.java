package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.FieldHeaderDTO;
import by.ralovets.qportal.dto.ResponseDTO;
import by.ralovets.qportal.dto.ResultsPageDTO;
import by.ralovets.qportal.dto.ResultsPageRequestDTO;
import by.ralovets.qportal.dto.mapper.ResponseMapper;
import by.ralovets.qportal.model.Response;
import by.ralovets.qportal.repository.AnswerRepository;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.ResponseRepository;
import by.ralovets.qportal.service.ResultService;
import by.ralovets.qportal.service.util.Paginator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static by.ralovets.qportal.service.util.IterableUtils.asList;
import static by.ralovets.qportal.service.util.IterableUtils.asStream;

@Service
@AllArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/greetings";

    private final Map<String, ResultsPageRequestDTO> subscribersMap = new HashMap<>();

    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final FieldRepository fieldRepository;

    @Override
    public ResultsPageDTO getResults(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page - 1, count);

        Page<Response> responsesPage = responseRepository.findAll(pageable);

        List<ResponseDTO> responses = StreamSupport
                .stream(responsesPage.spliterator(), false)
                .map(response -> ResponseMapper.mapToDTO(asList(answerRepository.findByResponseId(response.getId()))
                )).collect(Collectors.toList());

        return new ResultsPageDTO(
                responses,
                responsesPage.getTotalPages(),
                responsesPage.getTotalElements(),
                page
        );
    }

    @Override
    public List<FieldHeaderDTO> getFieldHeaders() {
        return asStream(fieldRepository.findAll())
                .map(field -> new FieldHeaderDTO(field.getId(), field.getLabel()))
                .collect(Collectors.toList());
    }

    @Override
    public void addSubscriber(String username, ResultsPageRequestDTO request) {
        subscribersMap.put(username, request);
    }

    /**
     * Sends messages with responses to all subscribed users.
     */
    public void sendResponses() {
        List<ResponseDTO> responses = asStream(responseRepository.findAll())
                .map(response -> ResponseMapper.mapToDTO(asList(answerRepository.findByResponseId(response.getId()))
                )).collect(Collectors.toList());

        Paginator<ResponseDTO> paginator = new Paginator<>(responses);

        for (String username : subscribersMap.keySet()) {
            ResultsPageDTO response = new ResultsPageDTO(
                    paginator.getElements(subscribersMap.get(username).getPage(), subscribersMap.get(username).getCount()),
                    paginator.getTotalPages(subscribersMap.get(username).getCount()),
                    (long) responses.size(),
                    subscribersMap.get(username).getPage()
            );

            simpMessagingTemplate.convertAndSendToUser(username, WS_MESSAGE_TRANSFER_DESTINATION, response);
        }
    }
}
