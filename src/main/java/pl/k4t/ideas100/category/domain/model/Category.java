package pl.k4t.ideas100.category.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.k4t.ideas100.question.domain.model.Question;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories")
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NotBlank(message = "{ideas.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Question> questions;

    public Category() {
        this.id = UUID.randomUUID();
    }

    public Category(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
