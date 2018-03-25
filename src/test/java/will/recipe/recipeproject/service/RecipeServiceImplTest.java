package will.recipe.recipeproject.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() {
        final Recipe recipe = new Recipe();
        final Set<Recipe> recipeData = new HashSet<Recipe>();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);

        final Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1L);
        verify(recipeRepository, times(1)).findAll();
    }
}