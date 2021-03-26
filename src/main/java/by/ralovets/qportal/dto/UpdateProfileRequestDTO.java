package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class UpdateProfileRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    public UpdateProfileRequestDTO() {
    }

    public UpdateProfileRequestDTO(String email, String firstName, String lastName, String phoneNumber, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
