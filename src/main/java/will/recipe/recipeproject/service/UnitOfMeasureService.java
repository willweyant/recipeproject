package will.recipe.recipeproject.service;

import will.recipe.recipeproject.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
