package logica;

import java.util.ArrayList;
import java.util.List;

/**
 * representa el tipo físico de un ítem (ej: Libro, CD, DVD)
 * cada ítem pertenece a exactamente un Tipo
 * el sistema debe tener al menos un Tipo para poder crear los ítems
 */

public class Tipo {

    private int codigo;
    private String nombre;
    private List<Item> items;

    // constructor
    public Tipo(int codigo, String nombre) {
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

    // devuelve una copia de la lista para evitar modificaciones externas
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // gestión de los ítems
    /**
     * agrega un ítem a este tipo
     * la Controladora debe llamar este método al crear un ítem
     */
    
    // el item a asociar no puede ser null
    public void agregarItem(Item item) {
        if (item != null && !items.contains(item)) {
            items.add(item);
        }
    }

    /**
     * elimina un ítem de este tipo
     * se llama cuando el ítem es borrado del sistema o cambia de tipo
     */
    public void eliminarItem(Item item) {
        items.remove(item);
    }

    // representación
    @Override
    public String toString() {
        return "Tipo{codigo=" + codigo + ", nombre='" + nombre + "', items=" + items.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tipo)) return false;
        Tipo otro = (Tipo) o;
        return this.codigo == otro.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}