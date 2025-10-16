import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {

    // Instancia de la lógica de negocio
    private final GestionAcademica gestion;

    // Componentes de la interfaz
    private final JFrame ventana; // Usamos la instancia en lugar de extenderla
    private final JTextArea areaResultados;
    private final JTextField campoCodigo;
    private final JTextField campoNombre;
    private final JTextField campoCorreoCreditos;
    private final JComboBox<String> selectorTipo;

    public Main() {
        // 1. Inicialización de la gestión académica
        this.gestion = new GestionAcademica();

        // 2. CREACIÓN DE LA VENTANA (JFrame) USANDO EL CONSTRUCTOR (sin setTitle, setSize, etc.)
        this.ventana = new JFrame("Gestión Académica - Sin Setters/Getters");
        this.ventana.setSize(800, 600);
        this.ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Usando constante, no set
        this.ventana.setLayout(new BorderLayout()); // Usando constructor o método directo

        // --- Panel de Controles (Norte) ---
        JPanel panelControles = new JPanel(new GridLayout(3, 1, 5, 5));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Fila de campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(1, 4, 5, 5));
        campoCodigo = new JTextField(10);
        campoNombre = new JTextField(10);
        campoCorreoCreditos = new JTextField(10);
        selectorTipo = new JComboBox<>(new String[]{"Docente", "Materia"});

        panelCampos.add(new JLabel("Código (D/M):"));
        panelCampos.add(campoCodigo);
        panelCampos.add(new JLabel("Nombre/Busqueda:"));
        panelCampos.add(campoNombre);
        panelCampos.add(new JLabel("Correo/Créditos:"));
        panelCampos.add(campoCorreoCreditos);
        panelCampos.add(new JLabel("Tipo:"));
        panelCampos.add(selectorTipo);
        panelControles.add(panelCampos);

        // 2. Fila de botones de acción
        JPanel panelAcciones = new JPanel(new GridLayout(1, 4, 10, 5));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnFiltrar = new JButton("Filtrar Materia");

        panelAcciones.add(btnRegistrar);
        panelAcciones.add(btnActualizar);
        panelAcciones.add(btnFiltrar);
        panelControles.add(panelAcciones);

        // 3. Fila de botones de Listado
        JPanel panelListar = new JPanel(new GridLayout(1, 2, 10, 5));
        JButton btnListarDocentes = new JButton("Listar Docentes");
        JButton btnListarMaterias = new JButton("Listar Materias");

        panelListar.add(btnListarDocentes);
        panelListar.add(btnListarMaterias);
        panelControles.add(panelListar);

        this.ventana.add(panelControles, BorderLayout.NORTH);

        // --- Área de Resultados (Centro) ---
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        this.ventana.add(scrollPane, BorderLayout.CENTER);

        // --- Eventos de Botones ---
        btnRegistrar.addActionListener(this::manejarRegistro);
        btnActualizar.addActionListener(this::manejarActualizacion);
        btnFiltrar.addActionListener(this::manejarFiltro);
        btnListarDocentes.addActionListener(e -> mostrarResultados("--- DOCENTES REGISTRADOS ---\n" + gestion.obtenerListaDocentes()));
        btnListarMaterias.addActionListener(e -> mostrarResultados("--- MATERIAS REGISTRADAS ---\n" + gestion.obtenerListaMaterias()));

        // Contenido Inicial en la Ventana
        mostrarResultados("BIENVENIDO AL SISTEMA DE GESTIÓN ACADÉMICA\n" +
                "-----------------------------------------\n" +
                "DATOS INICIALES (DOCENTES):\n" +
                gestion.obtenerListaDocentes());

        this.ventana.setVisible(true); // Método necesario para mostrar la ventana
    }

    // --- Métodos de Manejo de Eventos (sin cambios, usan JOptionPane que no tiene setters) ---

    private void mostrarResultados(String mensaje) {
        // Usa el método append/replace, no set
        areaResultados.setText(mensaje);
    }

    private void manejarRegistro(ActionEvent e) {
        String codigo = campoCodigo.getText().trim();
        String nombre = campoNombre.getText().trim();
        String correoCreditos = campoCorreoCreditos.getText().trim();
        String tipo = (String) selectorTipo.getSelectedItem();

        if (codigo.isEmpty() || nombre.isEmpty() || correoCreditos.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = false;
        if (tipo.equals("Docente")) {
            if (!GestionAcademica.validarCorreoInstitucional(correoCreditos)) {
                JOptionPane.showMessageDialog(ventana, "Correo inválido. Debe ser @uni.edu o @uni.uni.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            gestion.registrarDocente(new Docente(codigo, nombre, correoCreditos));
            exito = true;
        } else { // Materia
            try {
                int creditos = Integer.parseInt(correoCreditos);
                gestion.registrarMateria(new Materia(codigo, nombre, creditos));
                exito = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "Créditos debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (exito) {
            JOptionPane.showMessageDialog(ventana, tipo + " registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarResultados("Registro exitoso. Revise la lista de " + tipo + "s.");
        }
    }

    private void manejarActualizacion(ActionEvent e) {
        String codigo = campoCodigo.getText().trim();
        String nombre = campoNombre.getText().trim();
        String correoCreditos = campoCorreoCreditos.getText().trim();
        String tipo = (String) selectorTipo.getSelectedItem();

        if (codigo.isEmpty() || nombre.isEmpty() || correoCreditos.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debe completar Código, Nombre y Correo/Créditos para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = false;
        if (tipo.equals("Docente")) {
            if (!GestionAcademica.validarCorreoInstitucional(correoCreditos)) {
                JOptionPane.showMessageDialog(ventana, "Correo inválido para actualización.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            exito = gestion.actualizarDocente(codigo, nombre, correoCreditos);
        } else { // Materia
            try {
                int creditos = Integer.parseInt(correoCreditos);
                exito = gestion.actualizarMateria(codigo, nombre, creditos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "Créditos debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (exito) {
            JOptionPane.showMessageDialog(ventana, tipo + " actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarResultados("Actualización exitosa del " + tipo + " con código " + codigo);
        } else {
            JOptionPane.showMessageDialog(ventana, tipo + " no encontrado o error de validación.", "Fallo en Actualización", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarFiltro(ActionEvent e) {
        String busqueda = campoNombre.getText().trim();
        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Ingrese el texto de búsqueda en el campo Nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultados = gestion.filtrarMateriasPorNombre(busqueda);
        mostrarResultados(resultados);
    }

    // El método main es el punto de inicio de la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}