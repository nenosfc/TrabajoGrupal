import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Calificaciones class.
 * <p>
 * This class tests the method getId_asignaturas, which returns the id of the assigned subject.
 */
public class CalificacionesTest {

    @Test
    public void testGetId_asignaturas_ShouldReturnCorrectId_ForValidInput() {
        // Arrange
        int idAlumno = 1;
        int idAsignaturas = 100;
        double nota1 = 7.5;
        double nota2 = 8.0;
        double nota3 = 9.0;

        Calificaciones calificaciones = new Calificaciones(idAlumno, idAsignaturas, nota1, nota2, nota3);

        // Act
        int actualIdAsignaturas = calificaciones.getId_asignaturas();

        // Assert
        assertEquals(100, actualIdAsignaturas);
    }

    @Test
    public void testGetId_asignaturas_ShouldReturnTheSameId_AfterObjectCreation() {
        // Arrange
        int idAlumno = 2;
        int idAsignaturas = 200;
        double nota1 = 6.5;
        double nota2 = 7.0;
        double nota3 = 8.0;

        Calificaciones calificaciones = new Calificaciones(idAlumno, idAsignaturas, nota1, nota2, nota3);

        // Act
        int result = calificaciones.getId_asignaturas();

        // Assert
        assertEquals(200, result);
    }

    @Test
    public void testGetId_asignaturas_ShouldWorkForDifferentSubjectIds() {
        // Test case 1: Arrange
        int idAlumno1 = 3;
        int idAsignaturas1 = 300;
        Calificaciones calificaciones1 = new Calificaciones(idAlumno1, idAsignaturas1, 7.0, 8.0, 6.0);

        // Act & Assert
        assertEquals(300, calificaciones1.getId_asignaturas());

        // Test case 2: Arrange
        int idAlumno2 = 4;
        int idAsignaturas2 = 400;
        Calificaciones calificaciones2 = new Calificaciones(idAlumno2, idAsignaturas2, 8.5, 7.5, 7.0);

        // Act & Assert
        assertEquals(400, calificaciones2.getId_asignaturas());
    }
}