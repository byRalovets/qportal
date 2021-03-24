package by.ralovets.qportal.dto;

import lombok.Data;

@Data
public class UpdatePasswordRequestDTO {

    private String oldPassword;
    private String newPassword;

    public UpdatePasswordRequestDTO() {
    }

    public UpdatePasswordRequestDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
