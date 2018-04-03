package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.IngredientCommand;
import will.recipe.recipeproject.command.UnitOfMeasureCommand;
import will.recipe.recipeproject.domain.Ingredient;
import will.recipe.recipeproject.domain.Recipe;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    private static final Recipe RECIPE = new Recipe();
    private static final BigDecimal AMOUNT = BigDecimal.ONE;
    private static final String DESCRIPTION = "Cheeseburger";
    private static final Long ID_VALUE = Long.valueOf(1L);
    private static final Long UOM_ID = Long.valueOf(2L);
    private IngredientCommandToIngredient converter;


    @Before
    public void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        //given
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);

        final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        final Ingredient ingredient = converter.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }

    @Test
    public void convertWithNullUOM() {
        //given
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setUnitOfMeasure(null);

        //when
        final Ingredient ingredient = converter.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}