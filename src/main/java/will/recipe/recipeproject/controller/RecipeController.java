package will.recipe.recipeproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.service.RecipeService;

@Log4j2
@Controller
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/show")
    public String showById(@PathVariable final String id, final Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(final Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    // Old way: @RequestMapping(name = "recipe", method = RequestMethod.POST)
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute final RecipeCommand command, final Model model) {
        final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        System.out.println("savedCommand id = " + savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable final String id, final Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable final String id) {
        log.debug("deleting recipe id = {}", id);

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }
}
