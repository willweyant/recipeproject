package will.recipe.recipeproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.converter.RecipeCommandToRecipe;
import will.recipe.recipeproject.converter.RecipeToRecipeCommand;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.RecipeRepository;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        final Iterable<Recipe> recipes = recipeRepository.findAll();
        final Recipe testRecipe = recipes.iterator().next();
        final RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        final RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
