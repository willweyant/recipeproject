package will.recipe.recipeproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import will.recipe.recipeproject.command.IngredientCommand;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.command.UnitOfMeasureCommand;
import will.recipe.recipeproject.service.IngredientService;
import will.recipe.recipeproject.service.RecipeService;
import will.recipe.recipeproject.service.UnitOfMeasureService;

@Log4j2
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(final RecipeService recipeService, final IngredientService ingredientService,
                                final UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable final String recipeId, final Model model) {
        log.debug("Getting ingredient list for recipe id: {}", recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable final String recipeId,
                                 @PathVariable final String id, final Model model) {
        log.debug("Getting ingredient for recipe id {} and ingredient id {}", recipeId, id);

        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable final String recipeId, final Model model) {
        final RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //TODO: ADD BETTER ERROR HANDLING

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("unitOfMeasureList", unitOfMeasureService.listAllUnitOfMeasures());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable final String recipeId,
                                         @PathVariable final String id, final Model model) {
        log.debug("Updating recipe id {}'s ingredient id {}", recipeId, id);

        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("unitOfMeasureList", unitOfMeasureService.listAllUnitOfMeasures());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute final IngredientCommand command) {
        final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("saved recipe id: {}", command.getRecipeId());
        log.debug("saved ingredient id: {}", command.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable final String recipeId,
                                   @PathVariable final String id) {
        log.debug("Deleting recipe id {}'s ingredient id {}", recipeId, id);
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
