package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.CategoryCommand;
import will.recipe.recipeproject.command.IngredientCommand;
import will.recipe.recipeproject.command.NoteCommand;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.domain.Difficulty;
import will.recipe.recipeproject.domain.Recipe;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final Integer PREP_TIME = Integer.valueOf("7");
    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = Integer.valueOf("3");
    private static final String SOURCE = "Source";
    private static final String URL = "Some URL";
    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;
    private RecipeCommandToRecipe converter;


    @Before
    public void setUp() {
        converter = new RecipeCommandToRecipe(new NoteCommandToNote(), new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
        //given
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        final NoteCommand note = new NoteCommand();
        note.setId(NOTES_ID);

        recipeCommand.setNote(note);

        final CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        final CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        final IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        final IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        final Recipe recipe = converter.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNote().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}