import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GestionAcademica {
    private final List<Docente> listaDocentes;
    private final List<Materia> listaMaterias;

    public GestionAcademica() {
        this.listaDocentes = new ArrayList<>();
        this.listaMaterias = new ArrayList<>();

        // Datos iniciales
        registrarDocente(new Docente("D001", "María Gómez", "maria.gomez@uni.edu"));
        registrarDocente(new Docente("D002", "Juan Pérez", "juan.perez@uni.edu"));
        registrarMateria(new Materia("M001", "Programación Orientada a Objetos", 5));
        registrarMateria(new Materia("M002", "Bases de Datos", 4));
    }

    // --- FUNCION 1: Validar Correo Institucional ---
    public static boolean validarCorreoInstitucional(String correo) {
        // Patrón muy simplificado:
        // 1. Debe tener al menos un carácter al inicio.
        // 2. Debe tener un @.
        // 3. Debe terminar en .edu O .uni (con o sin otra extensión de país opcional).

        String regex = "^.+@[a-zA-Z0-9.-]+\\.(edu|uni)(\\.[a-zA-Z]{2,4})?$";

        return Pattern.compile(regex).matcher(correo).matches();
    }

    // --- Registrar ---
    public void registrarDocente(Docente docente) {
        this.listaDocentes.add(docente);
    }

    public void registrarMateria(Materia materia) {
        this.listaMaterias.add(materia);
    }

    // --- FUNCION 2: Listar Materias y Docentes (Retorna String para la GUI) ---
    public String obtenerListaDocentes() {
        if (listaDocentes.isEmpty()) {
            return "No hay docentes registrados.";
        }
        return listaDocentes.stream()
                .map(Docente::toString)
                .collect(Collectors.joining("\n"));
    }

    public String obtenerListaMaterias() {
        if (listaMaterias.isEmpty()) {
            return "No hay materias registradas.";
        }
        return listaMaterias.stream()
                .map(Materia::toString)
                .collect(Collectors.joining("\n"));
    }

    // --- FUNCION 3: Actualizar información de Materias y Docentes ---
    public boolean actualizarDocente(String codigoBusqueda, String nuevoNombre, String nuevoCorreo) {
        for (int i = 0; i < listaDocentes.size(); i++) {
            if (listaDocentes.get(i).obtenerCodigo().equals(codigoBusqueda)) {
                if (!validarCorreoInstitucional(nuevoCorreo)) {
                    return false; // Error de validación
                }
                Docente docenteActualizado = listaDocentes.get(i).actualizar(nuevoNombre, nuevoCorreo);
                listaDocentes.set(i, docenteActualizado);
                return true;
            }
        }
        return false; // Docente no encontrado
    }

    public boolean actualizarMateria(String codigoBusqueda, String nuevoNombre, int nuevosCreditos) {
        for (int i = 0; i < listaMaterias.size(); i++) {
            if (listaMaterias.get(i).obtenerCodigo().equals(codigoBusqueda)) {
                Materia materiaActualizada = listaMaterias.get(i).actualizar(nuevoNombre, nuevosCreditos);
                listaMaterias.set(i, materiaActualizada);
                return true;
            }
        }
        return false; // Materia no encontrada
    }

    // --- FUNCION 4: Filtrar al buscar Materias (Retorna String para la GUI) ---
    public String filtrarMateriasPorNombre(String parteDelNombre) {
        List<Materia> resultados = listaMaterias.stream()
                .filter(materia -> materia.obtenerNombre().toLowerCase().contains(parteDelNombre.toLowerCase()))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            return "No se encontraron coincidencias para '" + parteDelNombre + "'.";
        }

        return "RESULTADOS DEL FILTRO:\n" + resultados.stream()
                .map(Materia::toString)
                .collect(Collectors.joining("\n"));
    }
}