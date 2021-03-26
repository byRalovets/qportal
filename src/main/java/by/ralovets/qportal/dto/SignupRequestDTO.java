package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public SignupRequestDTO() {
    }

    public SignupRequestDTO(String email, String password, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
