package logica;

import java.util.ArrayList;
import java.util.List;

/**
 * representa una categoría temática que el usuario define para clasificar los ítems
 * un ítem puede pertenecer a ninguna o más categorías
 * las categorías son independientes, no necesitan que exista ninguna otra entidad
 */

public class Categoria {

    private int codigo;
    private String nombre;
    private List<Item> items;

    // constructor
    public Categoria(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.items  = new ArrayList<>();
    }

    // getters
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
    
    // devuelve una copia  para evitar modificaciones externas directas
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // gestión de los ítems
    /**
     * asocia un ítem a la categoría
     * la controladora es responsable de llamar este método al crear o modificar un ítem
     *
     * el ítem a asociar no puede ser null
     */
    public void agregarItem(Item item) {
        if (item != null && !items.contains(item)) {
            items.add(item);
        }
    }

    /**
     * desasocia un ítem de la categoría
     * se usa cuando el ítem es borrado del sistema o se le quita esta categoría
     * y también cuando la categoría misma es eliminada
     */
    public void eliminarItem(Item item) {
        items.remove(item);
    }

    // representación-
    @Override
    public String toString() {
        return "Categoria{codigo=" + codigo + ", nombre='" + nombre + "', items=" + items.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria otra = (Categoria) o;
        return this.codigo == otra.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}