package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.UnitOfMeasureCommand;
import will.recipe.recipeproject.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    private static final String DESCRIPTION = "description";
    private static final Long LONG_VALUE = new Long(1L);
    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        //when
        final UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}