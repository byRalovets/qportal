package by.ralovets.qportal.domain.entity;

import by.ralovets.qportal.domain.entity.util.FieldType;
import by.ralovets.qportal.domain.entity.util.PostgreSQLEnumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "fields")
@Data
@TypeDef(name = "field_type", typeClass = PostgreSQLEnumType.class)
public class Field {

    @Id
    @Column(name = "f_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "f_label")
    private String label;

    @Column(name = "f_required")
    private Boolean required;

    @Column(name = "f_is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "f_type", length = 30)
    @Type(type = "field_type")
    private FieldType type;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private Set<Option> options;

    public Field() {
    }
}
