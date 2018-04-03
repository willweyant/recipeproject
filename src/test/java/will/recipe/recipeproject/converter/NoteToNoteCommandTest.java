package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.NoteCommand;
import will.recipe.recipeproject.domain.Note;

import static org.junit.Assert.*;

public class NoteToNoteCommandTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String RECIPE_NOTE = "Note";
    private NoteToNoteCommand converter;

    @Before
    public void setUp() {
        converter = new NoteToNoteCommand();
    }

    @Test
    public void convert() {
        //given
        final Note note = new Note();
        note.setId(ID_VALUE);
        note.setRecipeNote(RECIPE_NOTE);

        //when
        final NoteCommand noteCommand = converter.convert(note);

        //then
        assertEquals(ID_VALUE, noteCommand.getId());
        assertEquals(RECIPE_NOTE, noteCommand.getRecipeNote());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Note()));
    }
}