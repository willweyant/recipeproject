package will.recipe.recipeproject.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import will.recipe.recipeproject.command.IngredientCommand;
import will.recipe.recipeproject.domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        final IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            command.setRecipeId(ingredient.getRecipe().getId());
        }
        command.setAmount(ingredient.getAmount());
        command.setDescription(ingredient.getDescription());
        command.setUnitOfMeasure(unitOfMeasureConverter.convert(ingredient.getUnitOfMeasure()));

        return command;
    }
}
