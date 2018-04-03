package will.recipe.recipeproject.service;

import will.recipe.recipeproject.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recipeId, Long ingredientId);
}
