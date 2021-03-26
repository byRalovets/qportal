package by.ralovets.qportal.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "answers")
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Long id;

    @Column(name = "a_response_id")
    private Long responseId;

    @Column(name = "a_field_id")
    private Integer fieldId;

    @Column(name = "a_text")
    private String text;

    public Answer() {
    }

    public Answer(String text) {
        this.text = text;
    }

    public Answer(Integer fieldId, String text) {
        this.fieldId = fieldId;
        this.text = text;
    }

    public Answer(Long id, Long responseId, Integer fieldId, String text) {
        this.id = id;
        this.responseId = responseId;
        this.fieldId = fieldId;
        this.text = text;
    }
}