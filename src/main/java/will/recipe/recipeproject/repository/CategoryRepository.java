package will.recipe.recipeproject.repository;

import org.springframework.data.repository.CrudRepository;
import will.recipe.recipeproject.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
