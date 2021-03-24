package by.ralovets.qportal.controller;

import by.ralovets.qportal.dto.FieldDTO;
import by.ralovets.qportal.service.FieldService;
import by.ralovets.qportal.service.exception.InvalidArgumentException;
import by.ralovets.qportal.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FieldController {

    private final FieldService fieldService;

    private static final String MSG_FIELD_NOT_FOUND = "Field not found! Check if the id is correct.";
    private static final String MSG_FIELD_NOT_CREATED = "The field cannot be created. Check the request!";
    private static final String MSG_FIELD_NOT_UPDATED = "The field cannot be updated. Check the request!";
    private static final String MSG_ERR_PAGE_OR_COUNT = "Page number and fields count must be more than 0!";

    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);

    @PostMapping
    ResponseEntity<?> newField(@RequestBody FieldDTO newFieldDTO) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(fieldService.createField(newFieldDTO));
        } catch (InvalidArgumentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(BAD_REQUEST)
                    .body(MSG_FIELD_NOT_CREATED);
        }
    }

    @GetMapping
    ResponseEntity<?> getFields(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer count) {
        try {
            return ResponseEntity.ok(fieldService.getAll(page, count));
        } catch (InvalidArgumentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(MSG_ERR_PAGE_OR_COUNT);
        }
    }

    @GetMapping("{id}")
    ResponseEntity<?> getField(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(fieldService.getOne(id));
        } catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(MSG_FIELD_NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateField(
            @RequestBody FieldDTO newFieldDTO, @PathVariable Integer id) {
        try {
            logger.info(newFieldDTO.toString());
            return ResponseEntity.ok(fieldService.updateField(newFieldDTO, id));
        } catch (InvalidArgumentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(MSG_FIELD_NOT_UPDATED);
        }
    }

    @DeleteMapping("{id}")
    void deleteField(@PathVariable Integer id) {
        fieldService.deleteField(id);
    }
}
