package by.ralovets.qportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
