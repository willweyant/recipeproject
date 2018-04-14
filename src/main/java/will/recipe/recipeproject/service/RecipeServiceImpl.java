package will.recipe.recipeproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.converter.RecipeCommandToRecipe;
import will.recipe.recipeproject.converter.RecipeToRecipeCommand;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.exceptions.NotFoundException;
import will.recipe.recipeproject.repository.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Inside getRecipes() using lombok to add log4j2 logger");
        final Set<Recipe> recipes = new HashSet<Recipe>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        final Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe Not Found. For id value: " + id);
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        final Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        final Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("savedRecipe Id = {}", savedRecipe.getId());
        System.out.println("savedRecipe id = " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(final Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        recipeRepository.deleteById(id);
    }
}
