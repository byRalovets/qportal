package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

    private String password;
    private String token;

    public ResetPasswordDTO() {}

    public ResetPasswordDTO(String password, String token) {
        this.password = password;
        this.token = token;
    }
}
