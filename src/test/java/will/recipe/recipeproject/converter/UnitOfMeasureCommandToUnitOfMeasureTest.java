package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.UnitOfMeasureCommand;
import will.recipe.recipeproject.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    private static final String DESCRIPTION = "description";
    private static final Long LONG_VALUE = Long.valueOf(1L);
    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convertTest() {
        //given
        final UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        final UnitOfMeasure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}