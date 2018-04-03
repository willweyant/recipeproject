package will.recipe.recipeproject.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final NoteCommandToNote noteConverter;
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(NoteCommandToNote noteConverter, CategoryCommandToCategory categoryConverter,
                                 IngredientCommandToIngredient ingredientConverter) {
        this.noteConverter = noteConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setSource(recipeCommand.getSource());
        recipe.setServings(recipeCommand.getServings());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setNote(noteConverter.convert(recipeCommand.getNote()));
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setImage(recipeCommand.getImage());

        if (!CollectionUtils.isEmpty(recipeCommand.getCategories())) {
            recipeCommand.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (!CollectionUtils.isEmpty(recipeCommand.getIngredients())) {
            recipeCommand.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}
