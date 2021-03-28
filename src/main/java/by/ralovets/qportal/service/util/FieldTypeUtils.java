package by.ralovets.qportal.service.util;

import by.ralovets.qportal.model.util.FieldType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static by.ralovets.qportal.model.util.FieldType.*;

public class FieldTypeUtils {

    private static final Set<FieldType> textualTypes = Set.of(SINGLELINE, MULTILINE, DATE);
    private static final Set<FieldType> optionalTypes = Set.of(RADIOBUTTON, CHECKBOX, COMBOBOX);

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
