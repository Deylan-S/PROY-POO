package logica;

import java.util.ArrayList;
import java.util.List;

/**
 * representa una persona a la que se le pueden hacer préstamos
 * guarda los datos de contacto y la lista de préstamos activos que tiene
 */
public class Usuario {

    private int codigo;
    private String nombre;
    private String telefono;
    private String correo;
    private List<Prestamo> prestamos;

    // constructor
    public Usuario(int codigo, String nombre, String telefono, String correo) {
        this.codigo    = codigo;
        this.nombre    = nombre;
        this.telefono  = telefono;
        this.correo    = correo;
        this.prestamos = new ArrayList<>();
    }

    // getters
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public List<Prestamo> getPrestamos() {
        return new ArrayList<>(prestamos);
    }

    // setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * gestión de préstamos
     * registra un préstamo activo para este usuario
     * la Controladora llama a este método al crear un nuevo préstamo
     */
    public void agregarPrestamo(Prestamo prestamo) {
        if (prestamo != null && !prestamos.contains(prestamo)) {
            prestamos.add(prestamo);
        }
    }

    /**
     * elimina un préstamo de la lista del usuario
     * se usa cuando el préstamo es finalizado
     */
    public void eliminarPrestamo(Prestamo prestamo) {
        prestamos.remove(prestamo);
    }

    // consultas
    /**
     * indica si el usuario tiene al menos un préstamo activo
     * la Controladora usa este método para impedir borrar usuarios con préstamos
     */
    public boolean tienePrestamos() {
        return !prestamos.isEmpty();
    }

    // representación
    @Override
    public String toString() {
        return "Usuario{codigo=" + codigo
                + ", nombre='" + nombre + "'"
                + ", telefono='" + telefono + "'"
                + ", correo='" + correo + "'"
                + ", prestamos=" + prestamos.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario otro = (Usuario) o;
        return this.codigo == otro.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}