public class Alumno {
//El constructor encarece a la clase como una clase concreta y no como una clase abstrata.
    private int id_alumno;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String email;
    private String telefono;
    private String direccion;

    public Alumno(int id_alumno,String nombre, String apellido, String fechaNacimiento,
                  String email, String telefono, String direccion) {
        this.id_alumno = id_alumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;

    }

    //El método getter, devuelvo el ID del alumno que tengo almacenado.
    public int getid_alumno() {
        return id_alumno;
    }

    //El método setter, asigno el ID del alumno que tengo almacenado.
    public void setid_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    //Método getter y setter para asignar el nombre al alumno
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //Método string, para escribir el nombre del alumno en la tabla de la base de datos.
    public String getApellido() {
        return apellido;
    }
    //Método string, para escribir el apellido del alumno en la tabla de la base de datos.
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //Método string, para escribir la fecha de nacimiento del alumno en la tabla de la base de datos.
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    //Método string, para escribir la fecha de nacimiento del alumno en la tabla de la base de datos.
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    //Método getter y setter para asignar el email al alumno
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Método getter y setter para asignar el telefono al alumno
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //Método getter y setter para asignar la dirección al alumno
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
