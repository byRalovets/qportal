package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.ResponseDTO;
import by.ralovets.qportal.model.Field;
import by.ralovets.qportal.model.Option;
import by.ralovets.qportal.model.Response;
import by.ralovets.qportal.repository.AnswerRepository;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.repository.ResponseRepository;
import by.ralovets.qportal.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static by.ralovets.qportal.dto.mapper.FieldMapper.mapToDTO;
import static by.ralovets.qportal.dto.mapper.ResponseMapper.mapToAnswers;
import static by.ralovets.qportal.service.util.IterableUtils.asList;

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
    public List<FieldDTO> getQuestionnaire() {
        List<FieldDTO> fieldDTOList = new ArrayList<>();

        StreamSupport
                .stream(fieldRepository.findAll(Sort.by("id")).spliterator(), false)
                .filter(Field::getIsActive)
                .forEach(field -> {
                    List<Option> options = asList(optionRepository.findByFieldId(field.getId()));
                    fieldDTOList.add(mapToDTO(field, options));
                });

        return fieldDTOList;
    }
}
