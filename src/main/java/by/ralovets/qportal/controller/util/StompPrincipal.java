package by.ralovets.qportal.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.security.Principal;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StompPrincipal implements Principal {

    private final String name;
}
