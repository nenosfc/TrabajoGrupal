// Importamos los módulos necesarios para poder usar la base de datos y construir la interfaz gráfica
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// La clase Interfaz se encarga de gestionar la interacción del usuario a través de ventanas Swing
// en la aplicación de gestión de alumnos
        public class Interfaz {
            // Objeto para manejar la conexión con la base de datos
            private final ConexionBBDD conexion;

            // Constructor que recibe una instancia de la clase de conexión a base de datos
            public Interfaz(ConexionBBDD conexion) {
                this.conexion = conexion;
            }

            // Método público que inicia la aplicación mostrando la ventana de login
            public void iniciar() {
                crearVentanaLogin();
            }

            // Método que crea y muestra el JFrame de inicio de sesión
            private void crearVentanaLogin() {
                JFrame loginFrame = new JFrame("Inicio de Sesión"); // Ventana principal de login
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setSize(300, 200);

                // Contenedor vertical para agrupar componentes
                Box contenedor = Box.createVerticalBox();
                Box fila1 = Box.createHorizontalBox(); // Fila para usuario
                Box fila2 = Box.createHorizontalBox(); // Fila para contraseña
                contenedor.add(fila1);
                contenedor.add(fila2);

                // Configuramos el layout y añadimos el contenedor al centro
                loginFrame.setLayout(new BorderLayout());
                loginFrame.add(contenedor, BorderLayout.CENTER);

                // Campo y etiqueta para el nombre de usuario
                JLabel usuarioLabel = new JLabel("Usuario:");
                JTextField usuarioField = new JTextField();
                fila1.add(usuarioLabel);
                fila1.add(usuarioField);

                // Campo y etiqueta para la contraseña
                JLabel passwordLabel = new JLabel("Contraseña:");
                JPasswordField passwordField = new JPasswordField();
                fila2.add(passwordLabel);
                fila2.add(passwordField);

                // Botón para iniciar sesión
                JButton loginButton = new JButton("Iniciar Sesión");
                contenedor.add(loginButton);

                // Listener para el botón: verifica credenciales y abre ventana principal o muestra error
                loginButton.addActionListener(e -> {
                    String usuario = usuarioField.getText();
                    String password = new String(passwordField.getPassword());

                    if (verificarCredenciales(usuario, password)) {
                        loginFrame.dispose(); // Cerrar ventana de login
                        abrirVentanaPrincipal(); // Abrir ventana principal de gestión
                    } else {
                        JOptionPane.showMessageDialog(loginFrame,
                                "Usuario o contraseña incorrectos",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Centrar y mostrar la ventana
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
            }

            // Método que comprueba las credenciales de forma estática (puede conectarse con BBDD en el futuro)
            private boolean verificarCredenciales(String usuario, String password) {
                return usuario.equals("Antonio") && password.equals("EclipseJava");
            }

            // Método que crea y muestra la ventana principal con botones para cada operación sobre alumnos
            private void abrirVentanaPrincipal() {
                JFrame mainFrame = new JFrame("Gestión de Alumnos");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setSize(400, 200);
                mainFrame.setLayout(new FlowLayout());

                // Botones para las distintas operaciones CRUD y de calificaciones
                JButton agregarButton = new JButton("Agregar Alumnos");
                JButton modificarButton = new JButton("Modificar Alumno");
                JButton eliminarButton = new JButton("Eliminar Alumno");
                JButton listarButton = new JButton("Listar Alumnos");
                JButton listarNotasButton = new JButton("Listar Notas de alumnos");
                JButton NotasButton = new JButton("Calificaciones");

                // Asociamos cada botón a su método correspondiente
                agregarButton.addActionListener(e -> mostrarFormularioAgregar());
                modificarButton.addActionListener(e -> mostrarFormularioModificar());
                eliminarButton.addActionListener(e -> mostrarFormularioEliminar());
                listarButton.addActionListener(e -> mostrarListaAlumnos());
                listarNotasButton.addActionListener(e -> mostrarListaCalificacionesAlumnos());
                NotasButton.addActionListener(e -> mostrarGestionCalificaciones());

                // Añadimos los botones al frame
                mainFrame.add(agregarButton);
                mainFrame.add(modificarButton);
                mainFrame.add(eliminarButton);
                mainFrame.add(listarNotasButton);
                mainFrame.add(listarButton);
                mainFrame.add(NotasButton);

                // Centramos y mostramos la ventana principal
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }

            // Muestra un formulario en un nuevo JFrame para agregar un alumno a la BBDD
            private void mostrarFormularioAgregar() {
                JFrame frame = new JFrame("Agregar Alumno");
                frame.setSize(500, 400);
                frame.setLayout(new BorderLayout());

                // Panel con GridBagLayout para organizar los campos de texto
                JPanel formPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Campos de formulario para los atributos del alumno
                JTextField id_alumnoField = new JTextField(20);
                JTextField nombreField = new JTextField(20);
                JTextField apellidoField = new JTextField(20);
                JTextField fechaField = new JTextField("YYYY-MM-DD", 20);
                JTextField emailField = new JTextField(20);
                JTextField telefonoField = new JTextField(20);
                JTextField direccionField = new JTextField(20);

                // Método auxiliar para añadir cada campo al panel
                addFormField(formPanel, "ID Alumno:", id_alumnoField, gbc, 0);
                addFormField(formPanel, "Nombre:", nombreField, gbc, 1);
                addFormField(formPanel, "Apellido:", apellidoField, gbc, 2);
                addFormField(formPanel, "Fecha Nacimiento:", fechaField, gbc, 3);
                addFormField(formPanel, "Email:", emailField, gbc, 4);
                addFormField(formPanel, "Teléfono:", telefonoField, gbc, 5);
                addFormField(formPanel, "Dirección:", direccionField, gbc, 6);

                // Panel de botones Guardar y Cancelar
                JPanel buttonPanel = new JPanel();
                JButton guardarButton = new JButton("Guardar");
                JButton cancelarButton = new JButton("Cancelar");

                // Listener para guardar: crea objeto Alumno y llama a conexión para insertar
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
                            frame.dispose(); // Cerramos el formulario al guardar correctamente
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

                // Listener para cancelar: cierra el formulario sin guardar
                cancelarButton.addActionListener(e -> frame.dispose());

                buttonPanel.add(guardarButton);
                buttonPanel.add(cancelarButton);

                // Añadimos panel de formulario y botones al frame
                frame.add(formPanel, BorderLayout.CENTER);
                frame.add(buttonPanel, BorderLayout.SOUTH);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            // Método auxiliar para añadir un campo con su etiqueta en GridBag
            private void addFormField(JPanel panel, String labelText,
                                      JTextField field, GridBagConstraints gbc, int y) {
                gbc.gridx = 0;
                gbc.gridy = y;
                panel.add(new JLabel(labelText), gbc);

                gbc.gridx = 1;
                panel.add(field, gbc);
            }

            // Muestra un diálogo para solicitar el ID de un alumno y, si existe, abre el formulario de edición
            private void mostrarFormularioModificar() {
                String idAlumno = JOptionPane.showInputDialog(null,
                        "Ingrese el ID del alumno a modificar:",
                        "Modificar Alumno",
                        JOptionPane.QUESTION_MESSAGE);

                if (idAlumno != null && !idAlumno.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idAlumno);
                        Alumno alumno = conexion.obtenerAlumno(id); // Obtiene datos del alumno
                        if (alumno != null) {
                            mostrarFormularioEdicion(id, alumno); // Abre formulario con datos precargados
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

            // Muestra un diálogo para solicitar el ID y eliminar un alumno tras confirmación
            private void mostrarFormularioEliminar() {
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

            // Muestra una tabla con todos los alumnos registrados en la base de datos
            private void mostrarListaAlumnos() {
                JFrame frame = new JFrame("Lista de Alumnos");
                frame.setSize(800, 400);

                List<Alumno> alumnos = conexion.listarAlumnos(); // Obtiene lista de objetos Alumno
                String[] columnNames = {"ID", "Nombre", "Apellido", "Fecha Nacimiento",
                        "Email", "Teléfono", "Dirección"};
                Object[][] data = new Object[alumnos.size()][7];

                // Rellena la matriz de datos para la JTable
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

                // Creamos y mostramos la tabla dentro de un JScrollPane
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            // Muestra un formulario con los datos actuales del alumno para modificarlos
            private void mostrarFormularioEdicion(int idAlumno, Alumno alumno) {
                JFrame frame = new JFrame("Modificar Alumno");
                frame.setSize(500, 400);
                frame.setLayout(new BorderLayout());

                JPanel formPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Campos precargados con los datos existentes del alumno
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

                // Listener para guardar los cambios en la base de datos
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
                        frame.dispose(); // Cerramos el formulario tras modificar
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

                // Añadimos paneles y mostramos
                frame.add(formPanel, BorderLayout.CENTER);
                frame.add(buttonPanel, BorderLayout.SOUTH);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            // Muestra un formulario para introducir calificaciones de un alumno en cada trimestre
            private void mostrarGestionCalificaciones() {
                JFrame frame = new JFrame("Gestión de Calificaciones");
                frame.setSize(400, 300);
                frame.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Campos para ID alumno, ID asignatura y tres notas trimestrales
                JTextField idAlumnoField = new JTextField(20);
                JTextField idAsignaturaField = new JTextField(20);
                JTextField nota1Field = new JTextField(20);
                JTextField nota2Field = new JTextField(20);
                JTextField nota3Field = new JTextField(20);
                JTextField[] fields = {idAlumnoField, idAsignaturaField, nota1Field, nota2Field, nota3Field};
                String[] labels = {"ID Alumno:", "ID Asignatura:", "1er Trimestre:", "2do Trimestre:", "3er Trimestre:"};

                // Añadimos cada etiqueta y campo al frame
                for (int i = 0; i < 5; i++) {
                    gbc.gridx = 0;
                    gbc.gridy = i;
                    frame.add(new JLabel(labels[i]), gbc);

                    gbc.gridx = 1;
                    frame.add(fields[i], gbc);
                }

                // Botón para guardar calificaciones
                JButton submitButton = new JButton("Guardar Calificaciones");
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;

                submitButton.addActionListener(e -> {
                    try {
                        int idAlumno = Integer.parseInt(idAlumnoField.getText());
                        int idAsignatura = Integer.parseInt(idAsignaturaField.getText());
                        double nota1 = Double.parseDouble(nota1Field.getText());
                        double nota2  = Double.parseDouble(nota2Field.getText());
                        double nota3 = Double.parseDouble(nota3Field.getText());

                        // Llamada a método que actualiza las notas en la BBDD
                        boolean success = this.conexion.actualizarNotas(idAlumno, idAsignatura, nota1, nota2, nota3);
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "Calificaciones guardadas exitosamente");
                            frame.dispose(); // Cerramos una vez guardado
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error al guardar las calificaciones", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ignore) {
                        // Muestra diálogo de error si los valores no son numéricos válidos
                        JOptionPane.showMessageDialog(frame,
                                "Por favor, ingrese valores numéricos válidos",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

                frame.add(submitButton, gbc);

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            // Muestra una tabla con las calificaciones completas de todos los alumnos
            private void mostrarListaCalificacionesAlumnos() {
                JFrame frame = new JFrame("Mostrar Calificaciones");
                frame.setSize(800, 400);

                // Obtenemos lista de objetos Calificaciones (si se usa)
                List<Calificaciones> calificacionesList = conexion.listarCalificacionesAlumnos();
                String[] columnNames = { "Nombre", "Apellido","ID_Alumno", "Asignatura",
                        "Id_Asignatura", "Nota Trimestre 1","Nota Trimestre 2","Nota Trimestre 3","Nota Final"};

                // Obtenemos lista estructurada de cadenas con todos los datos necesarios
                LinkedList<LinkedList<String>> alumnosNotas = conexion.obtenerCalificacionesCompletas();
                Object[][] data = new Object[alumnosNotas.size()][9]; // 9 columnas según encabezados

                int i = 0;
                for (var fila : alumnosNotas) {
                    int j = 0;
                    for (var col : fila) {
                        data[i][j] = col;
                        j++;
                    }
                    i++;
                }

                // Creamos la JTable con los datos y la mostramos en un JScrollPane
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }
