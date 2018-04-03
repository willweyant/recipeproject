package will.recipe.recipeproject.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final NoteToNoteCommand noteConverter;
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(NoteToNoteCommand noteConverter, CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter) {
        this.noteConverter = noteConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setCookTime(recipe.getCookTime());
        command.setDescription(recipe.getDescription());
        command.setDifficulty(recipe.getDifficulty());
        command.setDirections(recipe.getDirections());
        command.setNote(noteConverter.convert(recipe.getNote()));
        command.setPrepTime(recipe.getPrepTime());
        command.setServings(recipe.getServings());
        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setImage(recipe.getImage());

        if (!CollectionUtils.isEmpty(recipe.getCategories())) {
            recipe.getCategories().forEach(category -> command.getCategories().add(categoryConverter.convert(category)));
        }

        if (!CollectionUtils.isEmpty(recipe.getIngredients())) {
            recipe.getIngredients().forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
