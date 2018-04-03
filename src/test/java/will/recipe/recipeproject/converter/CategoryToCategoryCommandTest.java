package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.CategoryCommand;
import will.recipe.recipeproject.domain.Category;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCRIPTION = "descript";
    private CategoryToCategoryCommand convter;

    @Before
    public void setUp() {
        convter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(convter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    public void convert() {
        //given
        final Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        final CategoryCommand categoryCommand = convter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}