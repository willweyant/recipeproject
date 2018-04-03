package will.recipe.recipeproject.converter;

import org.junit.Before;
import org.junit.Test;
import will.recipe.recipeproject.command.NoteCommand;
import will.recipe.recipeproject.domain.Note;

import static org.junit.Assert.*;

public class NoteCommandToNoteTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String RECIPE_NOTE = "Note";
    private NoteCommandToNote converter;

    @Before
    public void setUp() {
        converter = new NoteCommandToNote();

    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NoteCommand()));
    }

    @Test
    public void convert() {
        //given
        final NoteCommand notesCommand = new NoteCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNote(RECIPE_NOTE);

        //when
        final Note note = converter.convert(notesCommand);

        //then
        assertNotNull(note);
        assertEquals(ID_VALUE, note.getId());
        assertEquals(RECIPE_NOTE, note.getRecipeNote());
    }

}