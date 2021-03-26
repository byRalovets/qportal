package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldsPageDTO;
import by.ralovets.qportal.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    ResponseEntity<?> newField(@RequestBody FieldDTO newFieldDTO) {
        return ResponseEntity.status(CREATED)
                .body(fieldService.createField(newFieldDTO));
    }

    @GetMapping
    FieldsPageDTO getFields(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer count) {
        return fieldService.getAll(page, count);
    }

    @GetMapping("{id}")
    FieldDTO getField(@PathVariable Integer id) {
        return fieldService.getOne(id);
    }

    @PutMapping("{id}")
    FieldDTO updateField(
            @RequestBody FieldDTO newFieldDTO, @PathVariable Integer id) {
        return fieldService.updateField(newFieldDTO, id);
    }

    @DeleteMapping("{id}")
    void deleteField(@PathVariable Integer id) {
        fieldService.deleteField(id);
    }
}
