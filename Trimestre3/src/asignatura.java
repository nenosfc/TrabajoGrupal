public class asignatura {
    private int id_asignatura;
    private String nombre;

    public asignatura()
    {
        this.id_asignatura=0;
        this.nombre="";
    }
    public asignatura(int id_asignatura, String nombre)
    {
        this.id_asignatura=id_asignatura;
        this.nombre=nombre;
    }
    public int getId_asignatura() {
        return id_asignatura;
    }
    public void setId_asignatura(int id_asignatura) {}

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {}
}
