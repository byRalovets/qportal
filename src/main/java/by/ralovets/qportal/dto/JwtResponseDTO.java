package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class JwtResponseDTO {

    private String token;
    private final String type = "Bearer";
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public JwtResponseDTO() {
    }

    public JwtResponseDTO(String token, Long id, String email, String firstName, String lastName) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
