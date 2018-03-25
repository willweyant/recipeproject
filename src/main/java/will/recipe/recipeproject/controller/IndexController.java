package will.recipe.recipeproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import will.recipe.recipeproject.service.RecipeService;

@Log4j2
@Controller
public class IndexController {
    //    private CategoryRepository categoryRepository;
//    private UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;

//    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
//        this.categoryRepository = categoryRepository;
//        this.unitOfMeasureRepository = unitOfMeasureRepository;
//    }

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(final Model model) {
//        Optional<Category> american = categoryRepository.findByDescription("American");
//        Optional<UnitOfMeasure> ounce = unitOfMeasureRepository.findByDescription("Ounce");
//        Optional<Category> notFound = categoryRepository.findByDescription("Chinese");
//        System.out.println("american cat id = " + american.get().getId());
//        System.out.println("ounce cat id = " + ounce.get().getId());
//        System.out.println("unknown category id = " + notFound);
        model.addAttribute("recipes", recipeService.getRecipes());
        log.debug("getting index page");

        return "index";
    }
}
