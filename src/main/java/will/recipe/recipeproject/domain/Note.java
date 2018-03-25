package will.recipe.recipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNote;

    public Note() {
    }

    public Note(String recipeNote, Recipe recipe) {
        this.recipeNote = recipeNote;
        this.recipe = recipe;
    }
}
