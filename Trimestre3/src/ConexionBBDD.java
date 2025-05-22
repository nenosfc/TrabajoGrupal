

import java.sql.*;
import java.util.*;

public class ConexionBBDD {
        private Connection conexion;

        public boolean success() {
            return this.conexion!=null;
        }

        public boolean agregarAlumno(Alumno alumno) {
            if (this.conexion == null) { return false; };

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

        public boolean eliminarAlumno(int idAlumno) {
            if (conexion == null) { return false; }

            String sql = "DELETE FROM alumnos WHERE id_alumno = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, idAlumno);
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) { return false; }
        }

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

        public LinkedList<Alumno> listarAlumnos() {
            LinkedList<Alumno> alumnos = new LinkedList<>();

            if (conexion == null) { return alumnos; }

            String sql = "SELECT * FROM alumnos";
            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)
                )
            {
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

    public LinkedList<Map<String, Object>> obtenerCalificacionesCompletas() {
        LinkedList<Map<String, Object>> calificacionesLista = new LinkedList<>();

        String sql = """
            SELECT a.nombre, a.apellido, a.id_alumno, 
                   asig.nombre AS asignatura, asig.id_asignatura,
                   c.NotaTrimestre1, c.NotaTrimestre2, c.NotaTrimestre3, c.NotaFinal
            FROM calificaciones c
            JOIN alumnos a ON c.id_alumno = a.id_alumno
            JOIN asignaturas asig ON c.id_asignaturas = asig.id_asignatura
            ORDER BY a.apellido, a.nombre, asig.nombre
            """;

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("nombre", rs.getString("nombre"));
                fila.put("apellido", rs.getString("apellido"));
                fila.put("id_alumno", rs.getInt("id_alumno"));
                fila.put("asignatura", rs.getString("asignatura"));
                fila.put("id_asignatura", rs.getInt("id_asignatura"));
                fila.put("nota1", rs.getDouble("NotaTrimestre1"));
                fila.put("nota2", rs.getDouble("NotaTrimestre2"));
                fila.put("nota3", rs.getDouble("NotaTrimestre3"));
                fila.put("notaFinal", rs.getDouble("NotaFinal"));
                calificacionesLista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener calificaciones: " + e.getMessage());
        }

        return calificacionesLista;
    }

}


