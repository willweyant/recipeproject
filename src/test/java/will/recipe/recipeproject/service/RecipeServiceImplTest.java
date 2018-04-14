package will.recipe.recipeproject.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import will.recipe.recipeproject.converter.RecipeCommandToRecipe;
import will.recipe.recipeproject.converter.RecipeToRecipeCommand;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.exceptions.NotFoundException;
import will.recipe.recipeproject.repository.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() {
        final Recipe recipe = new Recipe();
        recipe.setId(1L);
        final Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        final Recipe recipeReturned = recipeService.findById(recipe.getId());

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {
        final Recipe recipe = new Recipe();
        final Set<Recipe> recipeData = new HashSet<Recipe>();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);

        final Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1L);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        //given
        final Long idToDelete = Long.valueOf(2L);

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(idToDelete);
    }

    @Test(expected = NotFoundException.class)
    public void testGetRecipeByIdNotFound() {
        //given
        final Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        final Recipe recipe = recipeService.findById(1L);

        //then
        //exception should be thrown
    }
}