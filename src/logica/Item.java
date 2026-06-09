package logica;

import java.util.ArrayList;
import java.util.List;

/**
 * representa un ítem físico (libro, CD, DVD, etc.)
 * pertenece a exactamente un Tipo y a cero o más Categorias
 * solo puede estar en un préstamo a la vez
 */
public class Item {

    private int codigo;
    private String nombre;
    private String descripcion;
    private Tipo tipo;
    private List<Categoria> categorias;
    private Prestamo prestamo;

    /**
     * constructor
     * crea un ítem y lo registra automáticamente en su tipo
     */
    public Item(int codigo, String nombre, String descripcion, Tipo tipo) {
        this.codigo      = codigo;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.tipo        = tipo;
        this.categorias  = new ArrayList<>();
        this.prestamo    = null;

        tipo.agregarItem(this);
    }

    // getters
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public List<Categoria> getCategorias() {
        return new ArrayList<>(categorias);
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    // setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

     // Cambia el tipo del ítem y actualiza la lista del tipo anterior y la del nuevo
    public void setTipo(Tipo tipo) {
        if (tipo == null) return;
        this.tipo.eliminarItem(this);
        this.tipo = tipo;
        tipo.agregarItem(this);
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    /**
     * gestión de categoría
     * asocia una categoría a este ítem y registra el ítem en la categoría
     */
    public void agregarCategoria(Categoria categoria) {
        if (categoria != null && !categorias.contains(categoria)) {
            categorias.add(categoria);
            categoria.agregarItem(this);
        }
    }

    /**
     * desasocia una categoría de este ítem y elimina el ítem de la categoría
     * se usa para modificar un ítem o al borrar una categoría del sistema
     */
    public void eliminarCategoria(Categoria categoria) {
        if (categorias.remove(categoria)) {
            categoria.eliminarItem(this);
        }
    }

    // consultas
    /**
     * indica si el ítem está actualmente en un préstamo activo
     * la Controladora usa este método para impedir borrar ítems prestados
     * (en caso de usar Borrar ítem)
     */
    public boolean estaEnPrestamo() {
        return prestamo != null;
    }

    // representación
    @Override
    public String toString() {
        return "Item{codigo=" + codigo
                + ", nombre='" + nombre + "'"
                + ", tipo='" + tipo.getNombre() + "'"
                + ", prestado=" + estaEnPrestamo() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item otro = (Item) o;
        return this.codigo == otro.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}