public class Asignatura {
    private int id_asignatura;
    private String nombre;

    public Asignatura() {
        this.id_asignatura = 0;
        this.nombre = "";
    }

    public Asignatura(int id_asignatura, String nombre) {
        this.id_asignatura = id_asignatura;
        this.nombre = nombre;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        if (id_asignatura >= 0) {
            this.id_asignatura = id_asignatura;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }
}