import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.evolutiongenerator.Population;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IReproduction;
import com.example.evolutiongenerator.interfaces.ITerrain;
import com.example.evolutiongenerator.maps.Globe;
import com.example.evolutiongenerator.reproduction.ReproductionA;
import com.example.evolutiongenerator.terrain.ForestedEquators;
import com.example.evolutiongenerator.variants.BehaviourVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PopulationTest {

    private static final int POPULATION_SIZE = 10;
    private static final int MINIMUM_ENERGY_TO_REPRODUCTION = 10;
    private static final int GENOME_LENGTH = 10;
    private static final int ENERGY_USED_TO_REPRODUCTION = 10;
    private static final int MINIMUM_NUMBER_OF_MUTATIONS = 1;
    private static final int MAXIMUM_NUMBER_OF_MUTATIONS = 10;
    private static final int DAILY_ENERGY_CONSUMPTION = 10;
    private static final int INITIAL_ENERGY = 10;
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 10;

    private Population population;
    private IMap map;
    private IReproduction reproductionVariant;
    private ITerrain terrain;

    @BeforeEach
    public void setUp() {
        map = new Globe(MAP_WIDTH, MAP_HEIGHT);
        reproductionVariant = new ReproductionA();
        terrain = new ForestedEquators(map, 10, 10, 10);
        population = new Population(POPULATION_SIZE, MINIMUM_ENERGY_TO_REPRODUCTION,
                GENOME_LENGTH, ENERGY_USED_TO_REPRODUCTION, MINIMUM_NUMBER_OF_MUTATIONS,
                MAXIMUM_NUMBER_OF_MUTATIONS, map, reproductionVariant, terrain, BehaviourVariant.FULL_PREDESTINATION,
                DAILY_ENERGY_CONSUMPTION, INITIAL_ENERGY);
    }

    @Test
    public void testGenerateNewPopulation() {
        // Check that the correct number of animals are created
        assertEquals(POPULATION_SIZE, population.getLiveAnimals().size());
    }
}