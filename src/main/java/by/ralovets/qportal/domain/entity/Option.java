package by.ralovets.qportal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "field_options")
@Data
public class Option {

    @Id
    @Column(name = "fo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fo_text")
    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fo_field_id")
    private Field field;

    public Option() {
    }

    public Option(String text, Field field) {
        this.text = text;
    }
}
