package will.recipe.recipeproject.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import will.recipe.recipeproject.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void findByDescription() {
        final Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", teaspoonOptional.get().getDescription());
    }
}