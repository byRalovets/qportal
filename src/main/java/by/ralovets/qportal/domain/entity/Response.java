package by.ralovets.qportal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "responses")
@Data
public class Response {

    @Id
    @Column(name = "r_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Response() {
    }

    public Response(Long id) {
        this.id = id;
    }
}