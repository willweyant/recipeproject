package will.recipe.recipeproject.repository;

import org.springframework.data.repository.CrudRepository;
import will.recipe.recipeproject.domain.Recipe;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Optional<Recipe> findByDescription(String description);
}
