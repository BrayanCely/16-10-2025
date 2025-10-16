public class Docente {
    private final String codigo;
    private final String nombre;
    private final String correo;

    // Constructor (reemplaza a los setters)
    public Docente(String codigo, String nombre, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Método de acceso SOLO para la búsqueda en la lista
    public String obtenerCodigo() {
        return codigo;
    }

    // Actualización: Retorna un NUEVO Docente (inmutabilidad)
    public Docente actualizar(String nuevoNombre, String nuevoCorreo) {
        return new Docente(this.codigo, nuevoNombre, nuevoCorreo);
    }

    // Método para listado
    @Override
    public String toString() {
        return "Docente [Código: " + codigo + ", Nombre: " + nombre + ", Correo: " + correo + "]";
    }
}