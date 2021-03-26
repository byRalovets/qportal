package by.ralovets.qportal.service.impl;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.ResponseDTO;
import by.ralovets.qportal.model.Answer;
import by.ralovets.qportal.model.Field;
import by.ralovets.qportal.model.Option;
import by.ralovets.qportal.model.Response;
import by.ralovets.qportal.repository.AnswerRepository;
import by.ralovets.qportal.repository.FieldRepository;
import by.ralovets.qportal.repository.OptionRepository;
import by.ralovets.qportal.repository.ResponseRepository;
import by.ralovets.qportal.service.FormService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.ralovets.qportal.dto.mapper.FieldMapper.mapToDTO;
import static by.ralovets.qportal.dto.mapper.ResponseMapper.mapToAnswers;
import static by.ralovets.qportal.service.util.IterableUtils.asList;
import static by.ralovets.qportal.service.util.IterableUtils.asStream;

@Service
@AllArgsConstructor
public class FormServiceImpl implements FormService {

    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;

    @Override
    public void addResponse(ResponseDTO dto) {
        Response response = new Response();
        responseRepository.save(response);

        List<Answer> answers = mapToAnswers(dto);
        answers.forEach(answer -> answer.setResponseId(response.getId()));

        answerRepository.saveAll(answers);
    }

    @Override
    public List<FieldDTO> getFields() {
        List<FieldDTO> fieldDTOList = new ArrayList<>();

        asStream(fieldRepository.findAll(Sort.by("id")))
                .filter(Field::getIsActive)
                .forEach(field -> {
                    List<Option> options = asList(optionRepository.findByFieldId(field.getId()));
                    fieldDTOList.add(mapToDTO(field, options));
                });

        return fieldDTOList;
    }
}
