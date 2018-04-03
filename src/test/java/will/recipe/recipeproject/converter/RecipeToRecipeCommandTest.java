package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.domain.Category;
import will.recipe.recipeproject.domain.Difficulty;
import will.recipe.recipeproject.domain.Note;
import will.recipe.recipeproject.domain.Recipe;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeToRecipeCommandTest {
    private static final Long ID_VALUE = Long.valueOf(1L);
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final Integer PREP_TIME = Integer.valueOf("7");
    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = Integer.valueOf("4");
    private static final String SOURCE = "Source";
    private static final String URL = "url";
    private static final Long CAT_ID1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGREDIENT_ID1 = 3L;
    private static final Long INGREDIENT_ID2 = 4L;
    private static final Long NOTE_ID = 5L;
    private RecipeToRecipeCommand converter;


    @Before
    public void setUp() {
        converter = new RecipeToRecipeCommand(new NoteToNoteCommand(), new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {
        //given
        final Recipe recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        final Note note = new Note();
        note.setId(NOTE_ID);

        recipe.setNote(note);

        final Category category1 = new Category();
        category1.setId(CAT_ID1);

        final Category category2 = new Category();
        category2.setId(CAT_ID2);

    }

}