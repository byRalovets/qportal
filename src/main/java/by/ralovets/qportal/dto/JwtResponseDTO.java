package by.ralovets.qportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {

    private String token;
    private final String type = "Bearer";
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
