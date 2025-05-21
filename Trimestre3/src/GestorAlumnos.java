import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorAlumnos {
    private Connection conexion;

    public GestorAlumnos() {
        try {
            ConexionBBDD conn = new ConexionBBDD(null, null, null, null, null);
            if (conn.success()) {
                this.conexion = conn.getConnection();
            } else {
                throw new RuntimeException("No se pudo establecer la conexión con la base de datos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean agregarAlumno(Alumno alumno) {
        if (conexion == null) {
            mostrarError("No hay conexión con la base de datos");
            return false;
        }

        String sql = "INSERT INTO alumnos (id_alumno, nombre, apellido, fecha_nacimiento, email, telefono, direccion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, alumno.getid_alumno());
            pstmt.setString(2, alumno.getNombre());
            pstmt.setString(3, alumno.getApellido());
            pstmt.setString(4, alumno.getFechaNacimiento());
            pstmt.setString(5, alumno.getEmail());
            pstmt.setString(6, alumno.getTelefono());
            pstmt.setString(7, alumno.getDireccion());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            manejarErrorSQL("Error al agregar alumno", e);
            return false;
        }
    }



    public boolean modificarAlumno(int idAlumno, Alumno alumno) {
        if (conexion == null) {
            mostrarError("No hay conexión con la base de datos");
            return false;
        }

        String sql = "UPDATE alumnos SET nombre = ?, apellido = ?, fecha_nacimiento = ?, " +
                "email = ?, telefono = ?, direccion = ? WHERE id_alumno = ?";
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
            manejarErrorSQL("Error al modificar alumno", e);
            return false;
        }
    }

    public boolean eliminarAlumno(int idAlumno) {
        if (conexion == null) {
            mostrarError("No hay conexión con la base de datos");
            return false;
        }

        String sql = "DELETE FROM alumnos WHERE id_alumno = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idAlumno);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            manejarErrorSQL("Error al eliminar alumno", e);
            return false;
        }
    }

    public Alumno obtenerAlumno(int idAlumno) {
        if (conexion == null) {
            mostrarError("No hay conexión con la base de datos");
            return null;
        }

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
                alumno.setid_alumno(rs.getInt("id_alumno"));
                return alumno;
            }
        } catch (SQLException e) {
            manejarErrorSQL("Error al obtener alumno", e);
        }
        return null;
    }

    public List<Alumno> listarAlumnos() {
        if (conexion == null) {
            mostrarError("No hay conexión con la base de datos");
            return new ArrayList<>();
        }

        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
                alumno.setid_alumno(rs.getInt("id_alumno"));
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            manejarErrorSQL("Error al listar alumnos", e);
        }
        return alumnos;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void manejarErrorSQL(String mensaje, SQLException e) {
        String mensajeError = mensaje + ": ";
        if (e.getMessage().contains("Duplicate entry")) {
            mensajeError += "El email ya existe en la base de datos";
        } else {
            mensajeError += e.getMessage();
        }
        mostrarError(mensajeError);
        e.printStackTrace();
    }
}
