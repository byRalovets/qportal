package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class UpdateProfileRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public UpdateProfileRequestDTO() {
    }

    public UpdateProfileRequestDTO(String email, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
