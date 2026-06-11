package control;

import logica.Alerta;
import logica.Categoria;
import logica.Item;
import logica.Prestamo;
import logica.Usuario;
import logica.Tipo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * controladora principal del sistema de control de préstamos
 * este es el único punto de acceso a la lógica del negocio desde la interfaz
 * gestiona ítems, tipos, categorías, usuarios, préstamos, alertas y reportes
 */
public class Controladora {

    private Map<Integer, Item> items;
    private Map<Integer, Tipo> tipos;
    private Map<Integer, Categoria> categorias;
    private Map<Integer, Usuario> usuarios;
    private Map<Integer, Prestamo> prestamos;
    private int contadorItems;
    private int contadorTipos;
    private int contadorCategorias;
    private int contadorUsuarios;
    private int contadorPrestamos;
    private int contadorAlertas;

    // constructor
    public Controladora() {
        this.items          = new HashMap<>();
        this.tipos          = new HashMap<>();
        this.categorias     = new HashMap<>();
        this.usuarios       = new HashMap<>();
        this.prestamos      = new HashMap<>();
        this.contadorItems       = 0;
        this.contadorTipos       = 0;
        this.contadorCategorias  = 0;
        this.contadorUsuarios    = 0;
        this.contadorPrestamos   = 0;
        this.contadorAlertas     = 0;
    }

    //	 GESTIÓN DE TIPOS
    
    // crea un nuevo tipo y lo almacena en el sistema
    public int crearTipo(String nombre) {
        contadorTipos++;
        Tipo tipo = new Tipo(contadorTipos, nombre);
        tipos.put(contadorTipos, tipo);
        return contadorTipos;
    }

    // modifica el nombre de un tipo existente
    public void modificarTipo(int codigoTipo, String nombre) {
        Tipo tipo = tipos.get(codigoTipo);
        if (tipo != null) {
            tipo.setNombre(nombre);
        }
    }

    /**
     * elimina un tipo del sistema, los ítems que pertenecían a ese tipo
     * son reasignados al tipo genérico
     * y no se puede borrar el tipo genérico
     */
    public void borrarTipo(int codigoTipo) {
        if (codigoTipo == 1) return; // el tipo genérico no se puede borrar
        Tipo tipo = tipos.get(codigoTipo);
        if (tipo == null) return;

        Tipo tipoGenerico = tipos.get(1);
        for (Item item : tipo.getItems()) {
            item.setTipo(tipoGenerico);
        }
        tipos.remove(codigoTipo);
    }
    
    // retorna un tipo por su código
    public Tipo obtenerTipo(int codigoTipo) {
        return tipos.get(codigoTipo);
    }

    // retorna la lista de todos los tipos registrados en el sistema
    public List<Tipo> obtenerListadoTipos() {
        return new ArrayList<>(tipos.values());
    }
    
    // GESTIÓN DE CATEGORÍAS
    
    // Crea una nueva categoría y la almacena en el sistema
    public int crearCategoria(String nombre) {
        contadorCategorias++;
        Categoria categoria = new Categoria(contadorCategorias, nombre);
        categorias.put(contadorCategorias, categoria);
        return contadorCategorias;
    }
 
    // modifica el nombre de una categoría existente
    public void modificarCategoria(int codigoCategoria, String nombre) {
        Categoria categoria = categorias.get(codigoCategoria);
        if (categoria != null) {
            categoria.setNombre(nombre);
        }
    }
 
    /**
     * elimina una categoría del sistema
     * los ítems que pertenecían a ella pierden esa asociación
     * pero no se eliminan del sistema
     */
    public void borrarCategoria(int codigoCategoria) {
        Categoria categoria = categorias.get(codigoCategoria);
        if (categoria == null) return;
 
        for (Item item : categoria.getItems()) {
            item.eliminarCategoria(categoria);
        }
        categorias.remove(codigoCategoria);
    }
 
    // retorna una categoría por su código
    public Categoria obtenerCategoria(int codigoCategoria) {
        return categorias.get(codigoCategoria);
    }
 
    // retorna la lista de todas las categorías registradas en el sistema
    public List<Categoria> obtenerListadoCategorias() {
        return new ArrayList<>(categorias.values());
    }
    
    // GESTIÓN DE ÍTEMS
 
    /**
     * crea un nuevo ítem y lo almacena en el sistema
     * precondición: debe existir al menos un tipo en el sistema
     */
    public int crearItem(String nombre, String descripcion, int codigoTipo) {
        Tipo tipo = tipos.get(codigoTipo);
        if (tipo == null) return -1;
 
        contadorItems++;
        Item item = new Item(contadorItems, nombre, descripcion, tipo);
        items.put(contadorItems, item);
        return contadorItems;
    }
    
    // modifica los datos de un ítem que ya existe
    public void modificarItem(int codigoItem, String nombre, String descripcion, int codigoTipo) {
        Item item = items.get(codigoItem);
        Tipo tipo = tipos.get(codigoTipo);
        if (item == null || tipo == null) return;
 
        item.setNombre(nombre);
        item.setDescripcion(descripcion);
        item.setTipo(tipo);
    }
 
    /**
     * elimina un ítem del sistema
     * no se puede eliminar si está en un préstamo activo
     */
    public boolean borrarItem(int codigoItem) {
        Item item = items.get(codigoItem);
        if (item == null) return false;
        if (item.estaEnPrestamo()) return false;
 
        // desasociar de su tipo
        item.getTipo().eliminarItem(item);
 
        // desasociar de todas sus categorías
        for (Categoria categoria : item.getCategorias()) {
            categoria.eliminarItem(item);
        }
 
        items.remove(codigoItem);
        return true;
    }
 
    // retorna un ítem por su código
    public Item obtenerItem(int codigoItem) {
        return items.get(codigoItem);
    }
 
    // retorna la lista de todos los ítems registrados en el sistema
    public List<Item> obtenerListadoItems() {
        return new ArrayList<>(items.values());
    }

    // agrega una categoría a un ítem
    public void agregarCategoriaAItem(int codigoItem, int codigoCategoria) {
        Item item           = items.get(codigoItem);
        Categoria categoria = categorias.get(codigoCategoria);
        if (item != null && categoria != null) {
            item.agregarCategoria(categoria);
        }
    }

    // elimina una categoría de un ítem
    public void eliminarCategoriaDeItem(int codigoItem, int codigoCategoria) {
        Item item           = items.get(codigoItem);
        Categoria categoria = categorias.get(codigoCategoria);
        if (item != null && categoria != null) {
            item.eliminarCategoria(categoria);
        }
    }
    
    // GESTIÓN DE USUARIOS
 
    // crea un nuevo usuario y lo almacena en el sistema
    public int crearUsuario(String nombre, String telefono, String correo) {
        contadorUsuarios++;
        Usuario usuario = new Usuario(contadorUsuarios, nombre, telefono, correo);
        usuarios.put(contadorUsuarios, usuario);
        return contadorUsuarios;
    }
 
    // modifica los datos de un usuario que ya exista
    public void modificarUsuario(int codigoUsuario, String nombre, String telefono, String correo) {
        Usuario usuario = usuarios.get(codigoUsuario);
        if (usuario == null) return;
 
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
    }
 
    /**
     * elimina un usuario del sistema
     * (no se puede eliminar si tiene préstamos activos)
     */
    public boolean borrarUsuario(int codigoUsuario) {
        Usuario usuario = usuarios.get(codigoUsuario);
        if (usuario == null) return false;
        if (usuario.tienePrestamos()) return false;
 
        usuarios.remove(codigoUsuario);
        return true;
    }
 
    // retorna un usuario por su código
    public Usuario obtenerUsuario(int codigoUsuario) {
        return usuarios.get(codigoUsuario);
    }
 
    // retorna la lista de todos los usuarios registrados en el sistema
    public List<Usuario> obtenerListadoUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
    
    // GESTIÓN DE PRÉSTAMOS
 
    /**
     * crea un nuevo préstamo para un usuario
     * precondición: deben existir al menos un ítem y un usuario en el sistema
     */
    public int crearPrestamo(int codigoUsuario) {
        Usuario usuario = usuarios.get(codigoUsuario);
        if (usuario == null) return -1;
 
        contadorPrestamos++;
        Prestamo prestamo = new Prestamo(contadorPrestamos, usuario);
        prestamos.put(contadorPrestamos, prestamo);
        return contadorPrestamos;
    }
 
    /**
     * agrega un ítem a un préstamo existente
     * el ítem no debe estar en otro préstamo activo
     */
    public void agregarItemAPrestamo(int codigoPrestamo, int codigoItem) {
        Prestamo prestamo = prestamos.get(codigoPrestamo);
        Item item         = items.get(codigoItem);
        if (prestamo != null && item != null) {
            prestamo.agregarItem(item);
        }
    }
 
    /**
     * elimina un ítem de un préstamo durante su creación
     * esto hace que el ítem quede disponible para otros préstamos
     */
    public void eliminarItemDePrestamo(int codigoPrestamo, int codigoItem) {
        Prestamo prestamo = prestamos.get(codigoPrestamo);
        Item item         = items.get(codigoItem);
        if (prestamo != null && item != null) {
            prestamo.eliminarItem(item);
        }
    }
 
    /**
     * retorna un ítem individual de un préstamo activo
     * el ítem queda disponible para otros préstamos
     */
    public void retornarItemDePrestamo(int codigoPrestamo, int codigoItem) {
        eliminarItemDePrestamo(codigoPrestamo, codigoItem);
    }
 
    /**
     * finaliza un préstamo: libera todos sus ítems, elimina sus alertas
     * y lo elimina del sistema y del usuario
     */
    public void finalizarPrestamo(int codigoPrestamo) {
        Prestamo prestamo = prestamos.get(codigoPrestamo);
        if (prestamo == null) return;
 
        // liberar todos los ítems
        for (Item item : prestamo.getItems()) {
            item.setPrestamo(null);
        }
 
        // eliminar alertas
        prestamo.eliminarAlertas();
 
        // desasociar del usuario
        prestamo.getUsuario().eliminarPrestamo(prestamo);
 
        prestamos.remove(codigoPrestamo);
    }
 
    // agrega una alerta a un préstamo existente
    public int agregarAlertaAPrestamo(int codigoPrestamo, String mensaje,
                                      boolean esRecurrente, LocalDateTime tiempoActivacion) {
        Prestamo prestamo = prestamos.get(codigoPrestamo);
        if (prestamo == null) return -1;
 
        contadorAlertas++;
        new Alerta(contadorAlertas, mensaje, esRecurrente, tiempoActivacion, prestamo);
        return contadorAlertas;
    }
 
    // retorna un préstamo por su código
    public Prestamo obtenerPrestamo(int codigoPrestamo) {
        return prestamos.get(codigoPrestamo);
    }
 
    // retorna la lista de todos los préstamos activos en el sistema
    public List<Prestamo> obtenerListadoPrestamos() {
        return new ArrayList<>(prestamos.values());
    }
 
    /**
     * verifica todas las alertas del sistema al iniciar la aplicación
     * y retorna las que deben activarse
     */
    public List<Alerta> verificarAlertas() {
        List<Alerta> alertasActivas = new ArrayList<>();
        for (Prestamo prestamo : prestamos.values()) {
            for (Alerta alerta : prestamo.getAlertas()) {
                if (alerta.debeActivarse()) {
                    alertasActivas.add(alerta);
                }
            }
        }
        return alertasActivas;
    }
    
    // REPORTES
 
    /**
     * genera un reporte de los usuarios ordenados alfabéticamente
     * incluye los datos de cada usuario y sus préstamos activos
     */
    public List<Usuario> generarReportePorUsuario() {
        List<Usuario> lista = new ArrayList<>(usuarios.values());
        lista.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
        return lista;
    }
 
    /**
     * genera un reporte de ítems ordenado alfabéticamente
     * incluye los datos de cada ítem y si está prestado, a quién
     */
    public List<Item> generarReportePorItem() {
        List<Item> lista = new ArrayList<>(items.values());
        lista.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
        return lista;
    }
 
    /**
     * genera un reporte de categorías ordenado alfabéticamente
     * incluye los datos de cada categoría y los ítems que pertenecen a ella
     */
    public List<Categoria> generarReportePorCategoria() {
        List<Categoria> lista = new ArrayList<>(categorias.values());
        lista.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
        return lista;
    }
 
    /**
     * genera un reporte de tipos ordenado alfabéticamente
     * incluye los datos de cada tipo y los ítems clasificados en él
     */
    public List<Tipo> generarReportePorTipo() {
        List<Tipo> lista = new ArrayList<>(tipos.values());
        lista.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
        return lista;
    }
}