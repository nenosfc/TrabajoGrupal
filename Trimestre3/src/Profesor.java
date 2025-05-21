public class Profesor {
    private String Usuario;
    private String password;

    public Profesor()
    {
        this.Usuario="";
        this.password="";

    }

    public Profesor(String Usuario, String password) {
        this.Usuario = Usuario;
        this.password = password;
    }

    Profesor Antonio = new Profesor("Antonio","EclipseJava");
}


