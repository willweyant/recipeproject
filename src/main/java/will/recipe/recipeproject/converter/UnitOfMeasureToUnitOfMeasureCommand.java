package will.recipe.recipeproject.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import will.recipe.recipeproject.command.UnitOfMeasureCommand;
import will.recipe.recipeproject.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            return null;
        }

        final UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(unitOfMeasure.getId());
        command.setDescription(unitOfMeasure.getDescription());

        return command;
    }
}
