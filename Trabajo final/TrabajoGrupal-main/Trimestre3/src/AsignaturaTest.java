import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AsignaturaTest {

    /**
     * Test class for the Asignatura class.
     * This class focuses on testing the getId_asignatura() method, which retrieves the ID of the Asignatura object.
     */

    @Test
    public void testGetIdAsignatura_DefaultConstructor_ShouldReturnZero() {
        // Arrange
        Asignatura asignatura = new Asignatura();

        // Act
        int result = asignatura.getId_asignatura();

        // Assert
        assertEquals(0, result, "Default constructor should set id_asignatura to 0.");
    }

    @Test
    public void testGetIdAsignatura_ParameterizedConstructor_ShouldReturnAssignedValue() {
        // Arrange
        int expectedId = 5;
        Asignatura asignatura = new Asignatura(expectedId, "Mathematics");

        // Act
        int result = asignatura.getId_asignatura();

        // Assert
        assertEquals(expectedId, result, "Parameterized constructor should correctly set id_asignatura.");
    }

    @Test
    public void testGetIdAsignatura_AfterSetIdAsignatura_ShouldReturnUpdatedValue() {
        // Arrange
        Asignatura asignatura = new Asignatura();
        int newId = 10;

        // Act
        asignatura.setId_asignatura(newId);
        int result = asignatura.getId_asignatura();

        // Assert
        assertEquals(newId, result, "setId_asignatura should update id_asignatura to the given value.");
    }

    @Test
    public void testGetIdAsignatura_AfterSetIdAsignaturaWithNegativeValue_ShouldIgnoreNegativeValue() {
        // Arrange
        Asignatura asignatura = new Asignatura();
        int invalidId = -1;

        // Act
        asignatura.setId_asignatura(invalidId);
        int result = asignatura.getId_asignatura();

        // Assert
        assertEquals(0, result, "setId_asignatura should not update id_asignatura when given a negative value.");
    }
}