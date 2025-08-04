import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    void constructor_WhenHorsesIsNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_WhenHorsesIsEmpty_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(List.of())
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses_ReturnsSameListAsPassedToConstructor() {
        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expectedHorses.add(new Horse("Horse" + i, i + 1));
        }

        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        assertEquals(expectedHorses, hippodrome.getHorses());
    }

    @Test
    void move_CallsMoveOnAllHorses() {
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockHorses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();

        for (Horse mockHorse : mockHorses) {
            verify(mockHorse).move();
        }
    }

    @Test
    void getWinner_ReturnsHorseWithMaxDistance() {
        List<Horse> horses = List.of(
                new Horse("Slow", 1.0, 10.0),
                new Horse("Fast", 2.0, 20.0),
                new Horse("Medium", 1.5, 15.0)
        );

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();

        assertEquals("Fast", winner.getName());
        assertEquals(20.0, winner.getDistance());
    }

    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Disabled
    void main_ExecutionTimeLessThan22Seconds() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}