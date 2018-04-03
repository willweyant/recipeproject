package will.recipe.recipeproject.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import will.recipe.recipeproject.command.UnitOfMeasureCommand;
import will.recipe.recipeproject.converter.UnitOfMeasureToUnitOfMeasureCommand;
import will.recipe.recipeproject.domain.UnitOfMeasure;
import will.recipe.recipeproject.repository.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private UnitOfMeasureService service;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUnitOfMeasures() {
        //given
        final Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        final UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        final UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //when
        final Set<UnitOfMeasureCommand> commands = service.listAllUnitOfMeasures();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();

    }
}