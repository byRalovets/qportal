package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.dto.FieldsPageDTO;
import by.ralovets.qportal.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<?> newField(@RequestBody @Valid FieldDTO newFieldDTO) {
        return ResponseEntity.status(CREATED).body(fieldService.createField(newFieldDTO));
    }

    @GetMapping
    public FieldsPageDTO getFields(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "6") Integer count) {
        return fieldService.getAll(page, count);
    }

    @GetMapping("{id}")
    public FieldDTO getField(@PathVariable Integer id) {
        return fieldService.getOne(id);
    }

    @PutMapping("{id}")
    public FieldDTO updateField(@RequestBody @Valid FieldDTO newFieldDTO, @PathVariable Integer id) {
        return fieldService.updateField(newFieldDTO, id);
    }

    @DeleteMapping("{id}")
    public void deleteField(@PathVariable Integer id) {
        fieldService.deleteField(id);
    }
}
