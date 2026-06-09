package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * representa un préstamo activo de un conjunto de ítems a un usuario
 * un ítem solo puede pertenecer a un préstamo a la vez
 * un préstamo puede tener cero o más alertas asociadas
 */
public class Prestamo {

    private int codigo;
    private Usuario usuario;
    private List<Item> items;
    private List<Alerta> alertas;
    private LocalDate fecha;

    // constructor
     // crea un préstamo y lo registra en el usuario que corresponde
    public Prestamo(int codigo, Usuario usuario) {
        this.codigo  = codigo;
        this.usuario = usuario;
        this.items   = new ArrayList<>();
        this.alertas = new ArrayList<>();
        this.fecha   = LocalDate.now();

        usuario.agregarPrestamo(this);
    }

    // getters
    public int getCodigo() {
        return codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // gestión de ítems
    /**
     * agrega un ítem al préstamo y marca el ítem como prestado
     * solo se pueden agregar ítems que no estén en otro préstamo activo
     */
    public void agregarItem(Item item) {
        if (item != null && !item.estaEnPrestamo() && !items.contains(item)) {
            items.add(item);
            item.setPrestamo(this);
        }
    }

    /**
     * elimina un ítem del préstamo y lo marca como disponible
     * se usa tanto para eliminar ítems durante la creación del préstamo
     * como para retornar un ítem individual
     */
    public void eliminarItem(Item item) {
        if (items.remove(item)) {
            item.setPrestamo(null);
        }
    }

    // gestión de alertas

     // agrega una alerta a este préstamo
    public void agregarAlerta(Alerta alerta) {
        if (alerta != null && !alertas.contains(alerta)) {
            alertas.add(alerta);
        }
    }

     // elimina una alerta específica del préstamo
    public void eliminarAlerta(Alerta alerta) {
        alertas.remove(alerta);
    }

    /**
     * elimina todas las alertas del préstamo
     * se usa al finalizar el préstamo
     */
    public void eliminarAlertas() {
        alertas.clear();
    }

    // consultas
    /**
     * indica si el préstamo tiene al menos un ítem
     * sirve para validar antes de guardar el préstamo
     */
    public boolean tieneItems() {
        return !items.isEmpty();
    }

    // representación
    @Override
    public String toString() {
        return "Prestamo{codigo=" + codigo
                + ", usuario='" + usuario.getNombre() + "'"
                + ", items=" + items.size()
                + ", fecha=" + fecha + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo)) return false;
        Prestamo otro = (Prestamo) o;
        return this.codigo == otro.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}