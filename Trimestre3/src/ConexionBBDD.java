

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public boolean actualizarNotas(int idAlumno, double nota1, double nota2, double nota3) {
        String sql = "UPDATE alumnos SET NotaTrimestre1 = ?, NotaTrimestre2 = ?, NotaTrimestre3 = ? WHERE id_alumno = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setDouble(1, nota1);
            pstmt.setDouble(2, nota2);
            pstmt.setDouble(3, nota3);
            pstmt.setInt(4, idAlumno);

            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar las notas: " + e.getMessage());
            return false;
        }
    }

}


