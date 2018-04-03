package will.recipe.recipeproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.RecipeRepository;

import java.io.IOException;

@Log4j2
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(final Long recipeId, final MultipartFile file) {
        try {
            final Recipe recipe = recipeRepository.findById(recipeId).get();

            final Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

        } catch (IOException e) {
            //TODO: HANDLE BETTER
            log.error("Error occurred", e);
        }
    }
}
