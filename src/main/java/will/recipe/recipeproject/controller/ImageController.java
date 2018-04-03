package will.recipe.recipeproject.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.service.ImageService;
import will.recipe.recipeproject.service.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable final String id, final Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable final String id, @RequestParam("imagefile") final MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable final String id, final HttpServletResponse response) throws IOException {
        final RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        if (recipeCommand.getImage() != null) {
            final byte[] imageBytes = new byte[recipeCommand.getImage().length];

            int i = 0;

            for (Byte b : recipeCommand.getImage()) {
                imageBytes[i++] = b;
            }

            response.setContentType("image/jpeg");
            final InputStream is = new ByteArrayInputStream(imageBytes);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
