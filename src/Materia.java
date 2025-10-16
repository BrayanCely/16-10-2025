public class Materia {
    private final String codigo;
    private final String nombre;
    private final int creditos;

    public Materia(String codigo, String nombre, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerCodigo() {
        return codigo;
    }

    public Materia actualizar(String nuevoNombre, int nuevosCreditos) {
        return new Materia(this.codigo, nuevoNombre, nuevosCreditos);
    }

    @Override
    public String toString() {
        return "Materia [Código: " + codigo + ", Nombre: " + nombre + ", Créditos: " + creditos + "]";
    }
}