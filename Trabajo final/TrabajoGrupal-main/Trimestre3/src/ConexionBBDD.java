
//Importar la base de datos MySQL
import java.sql.*;
import java.util.*;

    //Clase pública para conectarse a la base de datos MySQL
public class ConexionBBDD {
    private Connection conexion;

    //Un booleano para comprobar que la base de datos se conecta correctamente
    public boolean success() {
        return this.conexion!=null;
    }

    //Un booleano para comprobar que la base de datos no se conecta correctamente
    public boolean agregarAlumno(Alumno alumno) {
        if (this.conexion == null) { return false; };

        //Un string para crear la sentencia SQL que se va a ejecutar en la base de datos
        String sql = """
                INSERT INTO alumnos (id_alumno, nombre, apellido, fecha_nacimiento, email, telefono, direccion)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, alumno.getid_alumno());
            pstmt.setString(2, alumno.getNombre());
            pstmt.setString(3, alumno.getApellido());
            pstmt.setString(4, alumno.getFechaNacimiento());
            pstmt.setString(5, alumno.getEmail());
            pstmt.setString(6, alumno.getTelefono());
            pstmt.setString(7, alumno.getDireccion());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    //Clase modificar alumno, para cambiar los datos de un alumno ya existente en la base de datos
    public boolean modificarAlumno(int idAlumno, Alumno alumno) {
        if (this.conexion == null) { return false; }

        String sql = """
                UPDATE alumnos SET nombre = ?, apellido = ?, fecha_nacimiento = ?,
                email = ?, telefono = ?, direccion = ? WHERE id_alumno = ?
                """;
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, alumno.getNombre());
            pstmt.setString(2, alumno.getApellido());
            pstmt.setString(3, alumno.getFechaNacimiento());
            pstmt.setString(4, alumno.getEmail());
            pstmt.setString(5, alumno.getTelefono());
            pstmt.setString(6, alumno.getDireccion());
            pstmt.setInt(7, idAlumno);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    //Una clase para eliminar un alumno de la base de datos, para que no pueda volver a ser ingresado
    public boolean eliminarAlumno(int idAlumno) {
        if (conexion == null) { return false; }

        String sql = "DELETE FROM alumnos WHERE id_alumno = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            System.out.println("Eliminando alumno con id: " + idAlumno);
            pstmt.setInt(1, idAlumno);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    //Una clase para obtener los datos de un alumno de la base de datos, para poder modificarlos
    public Alumno obtenerAlumno(int idAlumno) {
        if (conexion == null) { return null; }

        String sql = "SELECT * FROM alumnos WHERE id_alumno = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idAlumno);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getInt("id_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("fecha_nacimiento"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion")

                );
                return alumno;
            }
        } catch (SQLException e) {}
        return null;
    }

    //Una clase que enseña la lista de alumnos en la base de datos.
    public LinkedList<Alumno> listarAlumnos() {
        LinkedList<Alumno> alumnos = new LinkedList<>();

        if (conexion == null) { return alumnos; }

        String sql = "SELECT * FROM alumnos";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
            )
        {
            //Un while para poder sacar los datos del alumno existente
            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getInt("id_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("fecha_nacimiento"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                );
                alumnos.add(alumno);
            }
        } catch (SQLException e) {}
        return alumnos;
    }

    //Una clase pública para conectar la base de datos
    public ConexionBBDD(String host, String port, String user, String pass, String bd) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + bd + "?useSSL=false&serverTimezone=UTC";
        try {
            // Cargar el driver JDBC (opcional con JDBC 4.0+ pero recomendado)
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            this.conexion = null;
        }
    }

    //Un booleano que permite insertar notas en la base de datos para los alumnos.
    public boolean insertarNotas(int id_alumno, int id_asignaturas, double nota1, double nota2, double nota3) {
        String sql = """
            INSERT INTO calificaciones 
            (id_alumno, id_asignaturas, notaTrimestre1, notaTrimestre2, notaTrimestre3) 
            VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id_alumno);
            pstmt.setInt(2, id_asignaturas);
            pstmt.setDouble(3, nota1);
            pstmt.setDouble(4, nota2);
            pstmt.setDouble(5, nota3);

            int filasActualizadas = pstmt.executeUpdate();
            if (!(filasActualizadas > 0)) return insertarNotas(id_alumno, id_asignaturas, nota1, nota2, nota3);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar las notas: " + e.getMessage());
        }
        return false;
    }

    //Un booleano que permite actualizar las notas en la base de datos para los alumnos.
    public boolean actualizarNotas(int id_alumno, int id_asignaturas, double nota1, double nota2, double nota3) {
        String sql = "UPDATE calificaciones SET notaTrimestre1 = ?, notaTrimestre2 = ?, notaTrimestre3 = ? WHERE id_alumno = ? AND id_asignaturas = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setDouble(1, nota1);
            pstmt.setDouble(2, nota2);
            pstmt.setDouble(3, nota3);
            pstmt.setInt(4, id_alumno);
            pstmt.setInt(5, id_asignaturas);

            int filasActualizadas = pstmt.executeUpdate();
            if (!(filasActualizadas > 0)) return insertarNotas(id_alumno, id_asignaturas, nota1, nota2, nota3);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar las notas: " + e.getMessage());
        }
        return false;
    }

    //Un booleano que permite ver las notas de los alumnos, junto al id de la asignatura
    public Calificaciones getCalificaciones(int id_alumno, int id_asignatura) {

        Calificaciones calificaciones = null;

        if (conexion == null) {

            System.out.println("conexion");
            return calificaciones;
        }

        String sql = "SELECT * FROM calificaciones WHERE id_alumno = ? AND id_asignaturas = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id_alumno);
            pstmt.setInt(2, id_asignatura);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("try");
            if (rs.next()) {
                System.out.println("if");

                calificaciones = new Calificaciones(
                        rs.getInt("id_alumno"),
                        rs.getInt("id_asignaturas"),
                        rs.getDouble("NotaTrimestre1"),
                        rs.getDouble("NotaTrimestre2"),
                        rs.getDouble("NotaTrimestre3")
                );
            }
        } catch (SQLException e) {
            System.out.println("catch");

            System.out.println("Error al obtener calificaciones: " + e.getMessage());
        }

        return calificaciones;


    }

    public LinkedList<LinkedList<String>> obtenerCalificacionesCompletas() {
        LinkedList<LinkedList<String>> calificacionesLista = new LinkedList<>();

        String sql = """
            SELECT a.nombre, a.apellido, a.id_alumno, 
                   asig.nombre AS asignatura, asig.id,
                   c.NotaTrimestre1, c.NotaTrimestre2, c.NotaTrimestre3
            FROM calificaciones c
            JOIN alumnos a ON c.id_alumno = a.id_alumno
            JOIN asignaturas asig ON c.id_asignaturas = asig.id
            ORDER BY a.apellido, a.nombre, asig.nombre
            """;

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                var datos = new LinkedList<String>();
                datos.add(rs.getString("nombre"));
                datos.add(rs.getString("apellido"));
                datos.add("" + rs.getInt("id_alumno"));
                datos.add(rs.getString("asignatura"));
                datos.add( "" + rs.getInt("id"));
                double nota1 = rs.getDouble("NotaTrimestre1");
                double nota2 = rs.getDouble("NotaTrimestre2");
                double nota3 = rs.getDouble("NotaTrimestre3");
                datos.add("" + nota1);
                datos.add("" + nota2);
                datos.add("" + nota3);
                datos.add("" + (nota1 + nota2 + nota3) / 3);
                calificacionesLista.add(datos);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener calificaciones: " + e.getMessage());
        }

        return calificacionesLista;
    }

    //Un booleano que permite ver las notas de los alumnos, junto al id de la asignatura
    public List<Calificaciones> listarCalificacionesAlumnos() {
        List<Calificaciones> listaCalificaciones = new ArrayList<>();

        String sql = """
        SELECT id_alumno, id_asignaturas, NotaTrimestre1, 
               NotaTrimestre2, NotaTrimestre3
        FROM calificaciones
        """;

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Calificaciones calificacion = new Calificaciones(
                        rs.getInt("id_alumno"),
                        rs.getInt("id_asignaturas"),
                        rs.getDouble("NotaTrimestre1"),
                        rs.getDouble("NotaTrimestre2"),
                        rs.getDouble("NotaTrimestre3")
                );
                listaCalificaciones.add(calificacion);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener calificaciones: " + e.getMessage());
        }

        return listaCalificaciones;
    }
}