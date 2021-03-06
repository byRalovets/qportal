package by.ralovets.qportal.controller;

import by.ralovets.qportal.domain.dto.FieldDTO;
import by.ralovets.qportal.service.FieldService;
import by.ralovets.qportal.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("fields")
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    void newField(@RequestBody FieldDTO newFieldDTO) {
        fieldService.createField(newFieldDTO);
    }

    @GetMapping
    List<FieldDTO> allFields() {
        return fieldService.findAll();
    }

    @GetMapping("{id}")
    FieldDTO oneField(@PathVariable Integer id) {
        try {
            return fieldService.findFieldById(id);
        } catch (ServiceException e) {
            return null;
        }
    }

    @PutMapping("{id}")
    FieldDTO updateField(@RequestBody FieldDTO newFieldDTO, @PathVariable Integer id) {
        return fieldService.updateField(newFieldDTO, id);
    }

    @DeleteMapping("{id}")
    void deleteEmployee(@PathVariable Integer id) {
        fieldService.deleteField(id);
    }
}
