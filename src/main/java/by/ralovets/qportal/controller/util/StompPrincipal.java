package by.ralovets.qportal.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.security.Principal;

/**
 * Used to identify user connected via websocket.
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StompPrincipal implements Principal {

    private final String name;
}
