package by.ralovets.qportal.service.util;

import by.ralovets.qportal.domain.entity.util.FieldType;

import java.util.Arrays;
import java.util.List;

import static by.ralovets.qportal.domain.entity.util.FieldType.*;

public class FieldTypeUtils {

    private static final List<FieldType> textualTypes = Arrays.asList(SINGLELINE, MULTILINE, DATE);
    private static final List<FieldType> optionalTypes = Arrays.asList(RADIOBUTTON, CHECKBOX, COMBOBOX);

    private FieldTypeUtils() {
    }

    public static boolean wasTextBecomeOptions(FieldType was, FieldType become) {
        return textualTypes.contains(was) && optionalTypes.contains(become);
    }

    public static boolean wasOptionsBecomeText(FieldType was, FieldType become) {
        return optionalTypes.contains(was) && textualTypes.contains(become);
    }

    public static boolean wasOptionsBecomeOptions(FieldType was, FieldType become) {
        return optionalTypes.contains(was) && optionalTypes.contains(become);
    }
}
