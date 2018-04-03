package will.recipe.recipeproject.bootstrap;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import will.recipe.recipeproject.domain.Difficulty;
import will.recipe.recipeproject.domain.Ingredient;
import will.recipe.recipeproject.domain.Note;
import will.recipe.recipeproject.domain.Recipe;
import will.recipe.recipeproject.repository.CategoryRepository;
import will.recipe.recipeproject.repository.RecipeRepository;
import will.recipe.recipeproject.repository.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;

    public DevBootstrap(final RecipeRepository recipeRepository, final UnitOfMeasureRepository unitOfMeasureRepository,
                        final CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("loading bootstrap data");
    }

    private List<Recipe> getRecipes() {
        final List<Recipe> recipes = new ArrayList<Recipe>();
        final Recipe guacRecipe = new Recipe();
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setCookTime(0);
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setDirections("blah blah blah");
        guacRecipe.setPrepTime(10);
        guacRecipe.setServings(4);
        guacRecipe.setSource("Simply Recipes");
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setNote(new Note("Blah blah blah", guacRecipe));
        guacRecipe.addIngredient(new Ingredient("avocados", new BigDecimal("2"),
                unitOfMeasureRepository.findByDescription("Each").get()));
        guacRecipe.addIngredient(new Ingredient("kosher salt", new BigDecimal(".5"),
                unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        guacRecipe.getCategories().add(categoryRepository.findByDescription("American").get());
        guacRecipe.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        recipes.add(guacRecipe);

        final Recipe tacoRecipe = new Recipe();
        tacoRecipe.setDifficulty(Difficulty.MODERATE);
        tacoRecipe.setCookTime(20);
        tacoRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacoRecipe.setDirections("Blah blah blah blah");
        tacoRecipe.setPrepTime(10);
        tacoRecipe.setServings(6);
        tacoRecipe.setSource("Simply Recipes");
        tacoRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacoRecipe.setNote(new Note("Blah blah blah blah", tacoRecipe));
        tacoRecipe.addIngredient(new Ingredient("boneless/skinless chicken thighs", new BigDecimal("6"),
                unitOfMeasureRepository.findByDescription("Each").get()));
        tacoRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal("2"),
                unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        tacoRecipe.getCategories().add(categoryRepository.findByDescription("American").get());
        tacoRecipe.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        recipes.add(tacoRecipe);

        return recipes;
    }
}
