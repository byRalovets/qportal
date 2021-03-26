package by.ralovets.qportal.service.impl.validator;

import by.ralovets.qportal.dto.FieldDTO;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class FieldServiceValidator {

    private FieldServiceValidator() {}

    public static boolean validateFieldDTO(FieldDTO fieldDTO) {
        return nonNull(fieldDTO)
                && nonNull(fieldDTO.getLabel())
                && nonNull(fieldDTO.getType())
                && nonNull(fieldDTO.getIsActive())
                && nonNull(fieldDTO.getIsRequired())
                && nonNull(fieldDTO.getOptions());
    }


}
