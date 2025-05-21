import javax.swing.*;
public class Main extends JFrame {
    public static void main(String[] args) {
        // Configurar el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Probar la conexión a la base de datos
        ConexionBBDD conexion = new ConexionBBDD("sql7.freesqldatabase.com", "3306", "sql7779140", "UQp6YIRvGB", "sql7779140");
        if (!conexion.success()) {
            JOptionPane.showMessageDialog(null,
                "Error al conectar con la base de datos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } else {
            System.out.println("Conexión exitosa a la base de datos");
            // Iniciar la interfaz en el EDT
            SwingUtilities.invokeLater(() -> {
                Interfaz interfaz = new Interfaz(conexion);
                interfaz.iniciar();
            });
        }
    }
}