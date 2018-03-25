package will.recipe.recipeproject.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.service.RecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {
    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC_RootContext() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testMockMVC_EmptyContext() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        //given
        final Set<Recipe> recipes = new HashSet<Recipe>();
        recipes.add(new Recipe());

        final Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipes);

        final ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);

        //when
        final String returnValue = indexController.getIndexPage(model);

        //then
        assertEquals("index", returnValue);
        verify(model, times(1)).addAttribute(eq("recipes"), captor.capture());
        verify(recipeService, times(1)).getRecipes();
        final Set<Recipe> recipesInController = captor.getValue();
        assertEquals(2, recipesInController.size());
    }
}