package will.recipe.recipeproject.service;

import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
