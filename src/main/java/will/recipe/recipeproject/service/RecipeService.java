package will.recipe.recipeproject.service;

import will.recipe.recipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
