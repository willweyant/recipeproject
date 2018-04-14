package will.recipe.recipeproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import will.recipe.recipeproject.command.RecipeCommand;
import will.recipe.recipeproject.exceptions.NotFoundException;
import will.recipe.recipeproject.service.RecipeService;

import javax.validation.Valid;

@Log4j2
@Controller
public class RecipeController {
    private RecipeService recipeService;
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

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

        return RECIPE_RECIPEFORM_URL;
    }

    // Old way: @RequestMapping(name = "recipe", method = RequestMethod.POST)
    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") final RecipeCommand command, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return RECIPE_RECIPEFORM_URL;
        }

        final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        System.out.println("savedCommand id = " + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable final String id, final Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable final String id) {
        log.debug("deleting recipe id = {}", id);

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(final Exception exception) {
        log.error("Handling NotFoundexception");
        log.error(exception.getMessage(), exception);

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
