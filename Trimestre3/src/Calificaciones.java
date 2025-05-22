public class Calificaciones {

    private int id_alumno;
    private int id_asignaturas;
    private double NotaTrimestre1;
    private double NotaTrimestre2;
    private double NotaTrimestre3;
    private double NotaFinal;

    public Calificaciones(int id_alumno, int id_asignaturas,double NotaTrimestre1, double NotaTrimestre2, double NotaTrimestre3) {

        this.id_alumno = id_alumno;
        this.id_asignaturas = id_asignaturas;
        this.NotaTrimestre1 =NotaTrimestre1 ;
        this.NotaTrimestre2 = NotaTrimestre2;
        this.NotaTrimestre3 = NotaTrimestre3;
        this.NotaFinal = (NotaTrimestre1 + NotaTrimestre2 + NotaTrimestre3)/3;
    }

    public int getId_asignaturas() {
        return id_asignaturas;
    }
    public void setId_asignaturas(){
        this.id_asignaturas = id_asignaturas ;
        }

    public int getId_alumno() {
        return id_alumno;
    }
    public void setId_alumno(){
        this.id_alumno = id_alumno;
    }
    public double getNotaTrimestre1() {
        return NotaTrimestre1;
    }

    public void setNotaTrimestre1(double NotaTrimestre1) {
        this.NotaTrimestre1 = NotaTrimestre1;
    }

    public double getNotaTrimestre2() {
        return NotaTrimestre2;
    }

    public void setNotaTrimestre2(double NotaTrimestre2) {
        this.NotaTrimestre2 = NotaTrimestre2;
    }

    public double getNotaTrimestre3() {
        return NotaTrimestre3;
    }

    public void setNotaTrimestre3(double NotaTrimestre3) {
        this.NotaTrimestre3 = NotaTrimestre3;
    }
    public double getNotaFinal() {
        return NotaFinal;
    }
    public void setNotaFinal(double NotaFinal) {
        this.NotaFinal = NotaFinal;
    }
}
