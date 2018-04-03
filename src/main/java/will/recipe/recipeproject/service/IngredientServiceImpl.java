package will.recipe.recipeproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import will.recipe.recipeproject.command.IngredientCommand;
import will.recipe.recipeproject.converter.IngredientCommandToIngredient;
import will.recipe.recipeproject.converter.IngredientToIngredientCommand;
import will.recipe.recipeproject.domain.Ingredient;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.RecipeRepository;
import will.recipe.recipeproject.repository.UnitOfMeasureRepository;

import java.util.Optional;

@Log4j2
@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //TODO: IMPLEMENT EXCEPTION HANDLING
            log.error("recipe id not found: {}", recipeId);
        }

        final Recipe recipe = recipeOptional.get();

        final Optional<IngredientCommand> ingredientCommandOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //TODO: IMPLEMENT EXCEPTION HANDLING
            log.error("ingredient id not found: {}", recipeId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Transactional
    @Override
    public void deleteById(final Long recipeId, final Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);


        if (!recipeOptional.isPresent()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + recipeId);
        } else {
            final Recipe recipe = recipeOptional.get();
            final Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (!ingredientOptional.isPresent()) {
                //todo toss error if not found!
                log.error("Ingredient id {} not found for recipe id: {}", ingredientId, recipeId);
            } else {
                //remove ingredient
                final Ingredient savedIngredient = ingredientOptional.get();
                savedIngredient.setRecipe(null);
                recipe.getIngredients().remove(savedIngredient);
            }
        }
    }
}
