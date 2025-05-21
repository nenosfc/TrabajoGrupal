import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Interfaz {
    private static ConexionBBDD conexion;

    public Interfaz(ConexionBBDD conexion) {
        this.conexion = conexion;
    }

    public void iniciar() { crearVentanaLogin(); }

    private  void crearVentanaLogin() {
        JFrame loginFrame = new JFrame("Inicio de Sesión");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);

        Box contenedor = Box.createVerticalBox();

        Box fila1 = Box.createHorizontalBox();
        Box fila2 = Box.createHorizontalBox();
        contenedor.add(fila1);
        contenedor.add(fila2);

        loginFrame.setLayout(new BorderLayout());
        loginFrame.add(contenedor, BorderLayout.CENTER);

        JLabel usuarioLabel = new JLabel("Usuario:");
        JTextField usuarioField = new JTextField();

        fila1.add(usuarioLabel);
        fila1.add(usuarioField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField();

        fila2.add(passwordLabel);
        fila2.add(passwordField);

        JButton loginButton = new JButton("Iniciar Sesión");

        contenedor.add(loginButton);

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
        JButton NotasButton = new JButton("Calificaciones");

        // Agregamos los action listeners
        agregarButton.addActionListener(e -> mostrarFormularioAgregar());
        modificarButton.addActionListener(e -> mostrarFormularioModificar());
        eliminarButton.addActionListener(e -> mostrarFormularioEliminar());
        listarButton.addActionListener(e -> mostrarListaAlumnos());
        NotasButton.addActionListener(e -> mostrarGestionCalificaciones());

        mainFrame.add(agregarButton);
        mainFrame.add(modificarButton);
        mainFrame.add(eliminarButton);
        mainFrame.add(listarButton);
        mainFrame.add(NotasButton);


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

                if (conexion.agregarAlumno(alumno)) {
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
                Alumno alumno = conexion.obtenerAlumno(id);
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
                    if (conexion.eliminarAlumno(id)) {
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

        List<Alumno> alumnos = conexion.listarAlumnos();
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

            if (conexion.modificarAlumno(idAlumno, alumnoModificado)) {
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

    private void mostrarGestionCalificaciones() {
        JFrame frame = new JFrame("Gestión de Calificaciones");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Panel superior para el selector de alumno
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel idLabel = new JLabel("ID del Alumno:");
        JTextField idField = new JTextField(10);
        JButton buscarButton = new JButton("Buscar");
        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(buscarButton);

        // Panel central para las calificaciones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nota1Field = new JTextField(10);
        JTextField nota2Field = new JTextField(10);
        JTextField nota3Field = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(new JLabel("1er Trimestre:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(nota1Field, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(new JLabel("2º Trimestre:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(nota2Field, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        centerPanel.add(new JLabel("3er Trimestre:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(nota3Field, gbc);

        // Panel inferior para botones
        JPanel bottomPanel = new JPanel();
        JButton guardarButton = new JButton("Guardar Calificaciones");
        JButton cancelarButton = new JButton("Cancelar");
        bottomPanel.add(guardarButton);
        bottomPanel.add(cancelarButton);

        // Añadir funcionalidad al botón de búsqueda
        buscarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Alumno alumno = conexion.obtenerAlumno(id);
                if (alumno != null) {
                    nota1Field.setText(String.valueOf(alumno.getNotaTrimestre1()));
                    nota2Field.setText(String.valueOf(alumno.getNotaTrimestre2()));
                    nota3Field.setText(String.valueOf(alumno.getNotaTrimestre3()));
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "No se encontró el alumno con ID: " + id,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Por favor, ingrese un ID válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Añadir funcionalidad al botón de guardar
        guardarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                double nota1 = Double.parseDouble(nota1Field.getText());
                double nota2 = Double.parseDouble(nota2Field.getText());
                double nota3 = Double.parseDouble(nota3Field.getText());

                // Validar notas
                if (nota1 < 0 || nota1 > 10 || nota2 < 0 || nota2 > 10 || nota3 < 0 || nota3 > 10) {
                    JOptionPane.showMessageDialog(frame,
                            "Las notas deben estar entre 0 y 10",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Aquí deberías llamar al método de tu conexión para actualizar las notas
                if (conexion.actualizarNotas(id, nota1, nota2, nota3)) {
                    JOptionPane.showMessageDialog(frame,
                            "Calificaciones guardadas exitosamente");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Error al guardar las calificaciones",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Por favor, ingrese valores numéricos válidos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> frame.dispose());

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}