public class Alumno {

    private int id_alumno;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String email;
    private String telefono;
    private String direccion;
    private double notaTrimestre1;
    private double notaTrimestre2;
    private double notaTrimestre3;


    public Alumno(int id_alumno,String nombre, String apellido, String fechaNacimiento,
                  String email, String telefono, String direccion) {
        this.id_alumno = id_alumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.notaTrimestre1 =notaTrimestre1 ;
        this.notaTrimestre2 = notaTrimestre2;
        this.notaTrimestre3 = notaTrimestre3;

    }

    public int getid_alumno() {
        return id_alumno;
    }

    public void setid_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public double getNotaTrimestre1() {
        return notaTrimestre1;
    }

    public void setNotaTrimestre1(double notaTrimestre1) {
        this.notaTrimestre1 = notaTrimestre1;
    }

    public double getNotaTrimestre2() {
        return notaTrimestre2;
    }

    public void setNotaTrimestre2(double notaTrimestre2) {
        this.notaTrimestre2 = notaTrimestre2;
    }

    public double getNotaTrimestre3() {
        return notaTrimestre3;
    }

    public void setNotaTrimestre3(double notaTrimestre3) {
        this.notaTrimestre3 = notaTrimestre3;
    }

}
