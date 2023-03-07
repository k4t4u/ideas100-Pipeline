package pl.k4t.ideas100.question.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
@ToString
public class Answer {

    @Id
    private UUID id;

    @NotBlank(message = "{ideas.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    private String name;

    @ManyToOne
    private Question question;

    public Answer() {

        this.id = UUID.randomUUID();
    }

    public Answer(String name) {
        this();
        this.name = name;
    }
}