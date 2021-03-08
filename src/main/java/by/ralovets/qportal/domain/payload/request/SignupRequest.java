package by.ralovets.qportal.domain.payload.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String password;

    private String firstName;

    private String secondName;

    private String phoneNumber;

    public SignupRequest() {
    }

    public SignupRequest(String email, String password, String firstName, String secondName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }
}
