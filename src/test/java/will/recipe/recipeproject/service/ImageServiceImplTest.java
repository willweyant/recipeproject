package will.recipe.recipeproject.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.RecipeRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    private ImageService imageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        final Long id = 1L;
        final MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        final Recipe recipe = new Recipe();
        recipe.setId(id);
        final Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        final ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, file);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        assertEquals(file.getBytes().length, recipe.getImage().length);

    }
}