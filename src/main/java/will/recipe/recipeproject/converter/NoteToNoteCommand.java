package will.recipe.recipeproject.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import will.recipe.recipeproject.command.NoteCommand;
import will.recipe.recipeproject.domain.Note;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {

    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(final Note note) {
        if (note == null) {
            return null;
        }

        final NoteCommand command = new NoteCommand();
        command.setId(note.getId());
        command.setRecipeNote(note.getRecipeNote());

        return command;
    }
}
