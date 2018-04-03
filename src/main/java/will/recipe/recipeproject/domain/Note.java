package will.recipe.recipeproject.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
