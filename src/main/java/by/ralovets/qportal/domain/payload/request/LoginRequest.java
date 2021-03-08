package by.ralovets.qportal.domain.payload.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;

    private String password;

    public LoginRequest() {
    }
}
