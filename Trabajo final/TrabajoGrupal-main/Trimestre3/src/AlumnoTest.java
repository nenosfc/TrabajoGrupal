import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlumnoTest {

    /**
     * Tests for the setEmail method in the Alumno class.
     * This method updates the email address of an Alumno object.
     */

    @Test
    public void testSetEmailWithValidEmail() {
        // Arrange
        Alumno alumno = new Alumno(1, "Juan", "Perez", "2000-01-01", "original@example.com", "123456789", "Calle Falsa 123");
        String newEmail = "validemail@example.com";

        // Act
        alumno.setEmail(newEmail);

        // Assert
        assertEquals(newEmail, alumno.getEmail());
    }

    @Test
    public void testSetEmailWithEmptyString() {
        // Arrange
        Alumno alumno = new Alumno(1, "Juan", "Perez", "2000-01-01", "original@example.com", "123456789", "Calle Falsa 123");
        String newEmail = "";

        // Act
        alumno.setEmail(newEmail);

        // Assert
        assertEquals(newEmail, alumno.getEmail());
    }

    @Test
    public void testSetEmailWithNull() {
        // Arrange
        Alumno alumno = new Alumno(1, "Juan", "Perez", "2000-01-01", "original@example.com", "123456789", "Calle Falsa 123");
        String newEmail = null;

        // Act
        alumno.setEmail(newEmail);

        // Assert
        assertEquals(newEmail, alumno.getEmail());
    }

    @Test
    public void testSetEmailWithSpecialCharacters() {
        // Arrange
        Alumno alumno = new Alumno(1, "Juan", "Perez", "2000-01-01", "original@example.com", "123456789", "Calle Falsa 123");
        String newEmail = "email.with+special_chars@example.com";

        // Act
        alumno.setEmail(newEmail);

        // Assert
        assertEquals(newEmail, alumno.getEmail());
    }

    @Test
    public void testSetEmailWithLongEmail() {
        // Arrange
        Alumno alumno = new Alumno(1, "Juan", "Perez", "2000-01-01", "original@example.com", "123456789", "Calle Falsa 123");
        String newEmail = "averylongemailaddress@example.com";

        // Act
        alumno.setEmail(newEmail);

        // Assert
        assertEquals(newEmail, alumno.getEmail());
    }
}