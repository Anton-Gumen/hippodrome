import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {

    @Test
    void constructor_WhenNameIsNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 1.0)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", " \t\n\r"})
    void constructor_WhenNameIsBlank_ThrowsIllegalArgumentException(String name) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1.0)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_WhenSpeedIsNegative_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Name", -1.0)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_WhenDistanceIsNegative_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Name", 1.0, -1.0)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName_ReturnsNamePassedInConstructor() {
        String expectedName = "TestName";
        Horse horse = new Horse(expectedName, 1.0);
        assertEquals(expectedName, horse.getName());
    }

    @Test
    void getSpeed_ReturnsSpeedPassedInConstructor() {
        double expectedSpeed = 2.5;
        Horse horse = new Horse("Name", expectedSpeed);
        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    void getDistance_ReturnsDistancePassedInConstructor() {
        double expectedDistance = 3.0;
        Horse horse = new Horse("Name", 1.0, expectedDistance);
        assertEquals(expectedDistance, horse.getDistance());
    }

    @Test
    void getDistance_WhenTwoArgConstructor_ReturnsZero() {
        Horse horse = new Horse("Name", 1.0);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void move_CallsGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Name", 1.0).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void move_UpdatesDistanceCorrectly(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            double speed = 2.0;
            double initialDistance = 3.0;
            Horse horse = new Horse("Name", speed, initialDistance);
            horse.move();

            assertEquals(initialDistance + speed * randomValue, horse.getDistance());
        }
    }
}