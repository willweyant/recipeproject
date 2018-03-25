package will.recipe.recipeproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getRecipes() {
        log.debug("Inside getRecipes() using lombok to add log4j2 logger");
        final Set<Recipe> recipes = new HashSet<Recipe>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }
}
