package will.recipe.recipeproject.repository;

import org.springframework.data.repository.CrudRepository;
import will.recipe.recipeproject.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
