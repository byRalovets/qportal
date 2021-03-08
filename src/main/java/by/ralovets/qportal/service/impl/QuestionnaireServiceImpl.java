package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.domain.dto.FieldDTO;
import by.ralovets.qportal.domain.dto.ResponseDTO;
import by.ralovets.qportal.domain.dto.mapper.ResponseMapper;
import by.ralovets.qportal.domain.entity.Field;
import by.ralovets.qportal.domain.entity.Option;
import by.ralovets.qportal.domain.entity.Response;
import by.ralovets.qportal.repository.AnswerRepository;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.repository.ResponseRepository;
import by.ralovets.qportal.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static by.ralovets.qportal.domain.dto.mapper.FieldMapper.mapToDTO;
import static by.ralovets.qportal.domain.dto.mapper.ResponseMapper.mapToAnswers;

@Service
@AllArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;

    @Override
    public void newResponse(ResponseDTO dto) {
        Response response = new Response();
        responseRepository.save(response);

        answerRepository.saveAll(
                mapToAnswers(dto).stream()
                        .peek(answer -> answer.setResponseId(response.getId()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<ResponseDTO> getResponses() {
        return StreamSupport
                .stream(responseRepository.findAll().spliterator(), false)
                .map(response -> ResponseMapper
                        .mapToDTO(StreamSupport
                                .stream(answerRepository.findByResponseId(response.getId()).spliterator(), false)
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Override
    public List<FieldDTO> getQuestionnaire() {
        List<FieldDTO> fieldDTOList = new ArrayList<>();

        StreamSupport
                .stream(fieldRepository.findAll().spliterator(), false)
                .filter(Field::getIsActive)
                .forEach(field -> {
                    List<Option> options = StreamSupport
                            .stream(optionRepository.findByFieldId(field.getId()).spliterator(), false)
                            .collect(Collectors.toList());

                    fieldDTOList.add(mapToDTO(field, options));
                });

        return fieldDTOList;
    }
}
