// Clase Profesor que almacena información de acceso de un profesor
public class Profesor {
    // Atributo para almacenar el nombre de usuario del profesor
    private String Usuario;

    // Atributo para almacenar la contraseña del profesor
    private String password;

    // Constructor por defecto que inicializa los atributos con cadenas vacías
    public Profesor() {
        this.Usuario = "";
        this.password = "";
    }

    // Constructor que permite inicializar el objeto Profesor con un usuario y contraseña específicos
    public Profesor(String Usuario, String password) {
        this.Usuario = Usuario;
        this.password = password;
    }

    // Instancia de un objeto Profesor llamado Antonio con nombre de usuario y contraseña predefinidos
    Profesor Antonio = new Profesor("Antonio", "EclipseJava");
}

