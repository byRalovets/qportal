package by.ralovets.qportal.domain.payload.response;

import lombok.Data;

@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;

    public JwtResponse() {
    }

    public JwtResponse(String token, Long id, String email) {
        this.token = token;
        this.id = id;
        this.email = email;
    }
}
