import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Interfaz {
    private static GestorAlumnos gestor;

    public Interfaz() {
        gestor = new GestorAlumnos(); // Inicializamos el gestor
    }
    public void iniciar() {
        crearVentanaLogin();
    }

    private  void crearVentanaLogin() {
        JFrame loginFrame = new JFrame("Inicio de Sesión");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usuarioLabel = new JLabel("Usuario:");
        JTextField usuarioField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Iniciar Sesión");

        loginFrame.add(usuarioLabel);
        loginFrame.add(usuarioField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel());
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String password = new String(passwordField.getPassword());

            if (verificarCredenciales(usuario, password)) {
                loginFrame.dispose(); // Cerrar ventana de login
                abrirVentanaPrincipal();
            } else {
                JOptionPane.showMessageDialog(loginFrame,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private  boolean verificarCredenciales(String usuario, String password) {
        return usuario.equals("Antonio") && password.equals("EclipseJava");
    }

    private  void abrirVentanaPrincipal() {
        JFrame mainFrame = new JFrame("Gestión de Alumnos");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new FlowLayout());

        JButton agregarButton = new JButton("Agregar Alumnos");
        JButton modificarButton = new JButton("Modificar Alumno");
        JButton eliminarButton = new JButton("Eliminar Alumno");
        JButton listarButton = new JButton("Listar Alumnos");

        // Agregamos los action listeners
        agregarButton.addActionListener(e -> mostrarFormularioAgregar());
        modificarButton.addActionListener(e -> mostrarFormularioModificar());
        eliminarButton.addActionListener(e -> mostrarFormularioEliminar());
        listarButton.addActionListener(e -> mostrarListaAlumnos());

        mainFrame.add(agregarButton);
        mainFrame.add(modificarButton);
        mainFrame.add(eliminarButton);
        mainFrame.add(listarButton);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private  void mostrarFormularioAgregar() {
        JFrame frame = new JFrame("Agregar Alumno");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField id_alumnoField = new JTextField(20);
        JTextField nombreField = new JTextField(20);
        JTextField apellidoField = new JTextField(20);
        JTextField fechaField = new JTextField("YYYY-MM-DD", 20);
        JTextField emailField = new JTextField(20);
        JTextField telefonoField = new JTextField(20);
        JTextField direccionField = new JTextField(20);

        addFormField(formPanel, "ID Alumno:", id_alumnoField, gbc, 0);
        addFormField(formPanel, "Nombre:", nombreField, gbc, 1);
        addFormField(formPanel, "Apellido:", apellidoField, gbc, 2);
        addFormField(formPanel, "Fecha Nacimiento:", fechaField, gbc, 3);
        addFormField(formPanel, "Email:", emailField, gbc, 4);
        addFormField(formPanel, "Teléfono:", telefonoField, gbc, 5);
        addFormField(formPanel, "Dirección:", direccionField, gbc, 6);

        JPanel buttonPanel = new JPanel();
        JButton guardarButton = new JButton("Guardar");
        JButton cancelarButton = new JButton("Cancelar");

        guardarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(id_alumnoField.getText());
                Alumno alumno = new Alumno(
                        id,
                        nombreField.getText(),
                        apellidoField.getText(),
                        fechaField.getText(),
                        emailField.getText(),
                        telefonoField.getText(),
                        direccionField.getText()
                );

                if (gestor.agregarAlumno(alumno)) {
                    JOptionPane.showMessageDialog(frame, "Alumno agregado exitosamente");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Error al agregar alumno",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "El ID debe ser un número entero",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        cancelarButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private  void addFormField(JPanel panel, String labelText,
                                   JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private  void mostrarFormularioModificar() {
        String idAlumno = JOptionPane.showInputDialog(null,
            "Ingrese el ID del alumno a modificar:",
            "Modificar Alumno",
            JOptionPane.QUESTION_MESSAGE);

        if (idAlumno != null && !idAlumno.isEmpty()) {
            try {
                int id = Integer.parseInt(idAlumno);
                Alumno alumno = gestor.obtenerAlumno(id);
                if (alumno != null) {
                    mostrarFormularioEdicion(id, alumno);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "No se encontró el alumno con ID: " + id,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                    "ID inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private  void mostrarFormularioEliminar() {
        String idAlumno = JOptionPane.showInputDialog(null,
            "Ingrese el ID del alumno a eliminar:",
            "Eliminar Alumno",
            JOptionPane.QUESTION_MESSAGE);

        if (idAlumno != null && !idAlumno.isEmpty()) {
            try {
                int id = Integer.parseInt(idAlumno);
                int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de eliminar el alumno?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (gestor.eliminarAlumno(id)) {
                        JOptionPane.showMessageDialog(null,
                            "Alumno eliminado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Error al eliminar alumno",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                    "ID inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private  void mostrarListaAlumnos() {
        JFrame frame = new JFrame("Lista de Alumnos");
        frame.setSize(800, 400);

        List<Alumno> alumnos = gestor.listarAlumnos();
        String[] columnNames = {"ID", "Nombre", "Apellido", "Fecha Nacimiento",
            "Email", "Teléfono", "Dirección"};
        Object[][] data = new Object[alumnos.size()][7];

        for (int i = 0; i < alumnos.size(); i++) {
            Alumno alumno = alumnos.get(i);
            data[i][0] = alumno.getid_alumno();
            data[i][1] = alumno.getNombre();
            data[i][2] = alumno.getApellido();
            data[i][3] = alumno.getFechaNacimiento();
            data[i][4] = alumno.getEmail();
            data[i][5] = alumno.getTelefono();
            data[i][6] = alumno.getDireccion();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void mostrarFormularioEdicion(int idAlumno, Alumno alumno) {
        JFrame frame = new JFrame("Modificar Alumno");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField id_alumnoField = new JTextField(20);
        JTextField nombreField = new JTextField(alumno.getNombre(), 20);
        JTextField apellidoField = new JTextField(alumno.getApellido(), 20);
        JTextField fechaField = new JTextField(alumno.getFechaNacimiento(), 20);
        JTextField emailField = new JTextField(alumno.getEmail(), 20);
        JTextField telefonoField = new JTextField(alumno.getTelefono(), 20);
        JTextField direccionField = new JTextField(alumno.getDireccion(), 20);

        addFormField(formPanel, "ID Alumno:", id_alumnoField, gbc, 0);
        addFormField(formPanel, "Nombre:", nombreField, gbc, 1);
        addFormField(formPanel, "Apellido:", apellidoField, gbc, 2);
        addFormField(formPanel, "Fecha Nacimiento:", fechaField, gbc, 3);
        addFormField(formPanel, "Email:", emailField, gbc, 4);
        addFormField(formPanel, "Teléfono:", telefonoField, gbc, 5);
        addFormField(formPanel, "Dirección:", direccionField, gbc, 6);

        JPanel buttonPanel = new JPanel();
        JButton guardarButton = new JButton("Guardar Cambios");
        JButton cancelarButton = new JButton("Cancelar");

        guardarButton.addActionListener(e -> {
            Alumno alumnoModificado = new Alumno(
                    idAlumno,
                    nombreField.getText(),
                    apellidoField.getText(),
                    fechaField.getText(),
                    emailField.getText(),
                    telefonoField.getText(),
                    direccionField.getText()
            );

            if (gestor.modificarAlumno(idAlumno, alumnoModificado)) {
                JOptionPane.showMessageDialog(frame, "Alumno modificado exitosamente");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Error al modificar alumno",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}