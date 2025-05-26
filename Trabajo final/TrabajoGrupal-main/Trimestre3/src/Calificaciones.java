// Clase Calificaciones que representa las notas de un alumno en una asignatura
public class Calificaciones {

    // Identificador del alumno
    private int id_alumno;
    // Identificador de la asignatura
    private int id_asignaturas;
    // Notas obtenidas en cada uno de los tres trimestres
    private double NotaTrimestre1;
    private double NotaTrimestre2;
    private double NotaTrimestre3;
    // Nota final calculada como promedio de los tres trimestres
    private double NotaFinal;

    //Constructor de Calificaciones
    public Calificaciones(int id_alumno, int id_asignaturas, double NotaTrimestre1, double NotaTrimestre2, double NotaTrimestre3) {
        this.id_alumno = id_alumno;
        this.id_asignaturas = id_asignaturas;
        this.NotaTrimestre1 = NotaTrimestre1;
        this.NotaTrimestre2 = NotaTrimestre2;
        this.NotaTrimestre3 = NotaTrimestre3;
        // Cálculo de la nota final
        this.NotaFinal = (NotaTrimestre1 + NotaTrimestre2 + NotaTrimestre3) / 3;
    }

    //Obtiene el ID de la asignatura
    public int getId_asignaturas() {
        return id_asignaturas;
    }

    //Establece el ID de la asignatura
    public void setId_asignaturas() {
        this.id_asignaturas = id_asignaturas;
    }

    //Obtiene el ID del alumno
    public int getId_alumno() {
        return id_alumno;
    }

    //setter del ID del alumno
    public void setId_alumno() {
        this.id_alumno = id_alumno;
    }

    //Obtiene nota del primer trimestre
    public double getNotaTrimestre1() {
        return NotaTrimestre1;
    }

    //Establece nota del primer trimestre
    public void setNotaTrimestre1(double NotaTrimestre1) {
        this.NotaTrimestre1 = NotaTrimestre1;
    }

    //Obtiene nota del 2 trimestre
    public double getNotaTrimestre2() {
        return NotaTrimestre2;
    }

    //Establece nota del 2 trimestre
    public void setNotaTrimestre2(double NotaTrimestre2) {
        this.NotaTrimestre2 = NotaTrimestre2;
    }

    //Obtiene nota del tercer trimestre
    public double getNotaTrimestre3() {
        return NotaTrimestre3;
    }

    //Establece nota del tercer trimestre
    public void setNotaTrimestre3(double NotaTrimestre3) {
        this.NotaTrimestre3 = NotaTrimestre3;
    }

    //Obtiene la nota final
    public double getNotaFinal() {
        return NotaFinal;
    }

     //Setter para la nota final
    public void setNotaFinal(double NotaFinal) {
        this.NotaFinal = NotaFinal;
    }
}
