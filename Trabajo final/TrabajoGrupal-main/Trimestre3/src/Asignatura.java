public class Asignatura {
    //Atributos de la clase Asignatura
    private int id_asignatura;
    private String nombre;

    //Constructor de la clase Asignatura, que inicializa los atributos de la clase con valores por defecto.
    public Asignatura() {
        this.id_asignatura = 0;
        this.nombre = "";
    }

    public Asignatura(int id_asignatura, String nombre) {
        this.id_asignatura = id_asignatura;
        this.nombre = nombre;
    }

    //Métodos getter y setter para asignar el ID de la asignatura
    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        if (id_asignatura >= 0) {
            this.id_asignatura = id_asignatura;
        }
    }

    //Métodos getter y setter para asignar el nombre de la asignatura
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }
}