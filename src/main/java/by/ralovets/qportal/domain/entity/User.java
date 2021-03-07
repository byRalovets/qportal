package by.ralovets.qportal.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_password_hash")
    private String password;

    @Column(name = "u_first_name")
    private String firstName;

    @Column(name = "u_last_name")
    private String lastName;

    @Column(name = "u_phone_number")
    private String phoneNumber;

    public User() {
    }

    public User(Long id, String email, String password, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
