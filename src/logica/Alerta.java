package logica;

import java.time.LocalDateTime;

/**
 * representa una alerta asociada a un préstamo
 * puede ser de una sola vez o recurrente
 * se muestra al usuario cuando la aplicación inicia y la fecha y hora de activación se cumple
 */
public class Alerta {

    private int codigo;
    private String mensaje;
    private boolean esRecurrente;
    private LocalDateTime tiempoActivacion;
    private Prestamo prestamo;

    // constructor
    // crea una alerta y la registra en su préstamo correspondiente
    public Alerta(int codigo, String mensaje, boolean esRecurrente,
                  LocalDateTime tiempoActivacion, Prestamo prestamo) {
        this.codigo           = codigo;
        this.mensaje          = mensaje;
        this.esRecurrente     = esRecurrente;
        this.tiempoActivacion = tiempoActivacion;
        this.prestamo         = prestamo;

        prestamo.agregarAlerta(this);
    }

    // getters
    public int getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isEsRecurrente() {
        return esRecurrente;
    }

    public LocalDateTime getTiempoActivacion() {
        return tiempoActivacion;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    // setters
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setEsRecurrente(boolean esRecurrente) {
        this.esRecurrente = esRecurrente;
    }

    public void setTiempoActivacion(LocalDateTime tiempoActivacion) {
        this.tiempoActivacion = tiempoActivacion;
    }

    /**
     * consultas
     * indica si la alerta debe mostrarse al iniciar la aplicación
     * si es recurrente se activa cuando el momento actual es igual
     * o posterior al tiempoActivacion
     * si es de una sola vez, solo se activa en el minuto exacto de tiempoActivacion
     */
    public boolean debeActivarse() {
        LocalDateTime ahora = LocalDateTime.now();
        if (esRecurrente) {
            return !ahora.isBefore(tiempoActivacion);
        } else {
            return !ahora.isBefore(tiempoActivacion)
                && ahora.isBefore(tiempoActivacion.plusMinutes(1));
        }
    }

    // representación
    @Override
    public String toString() {
        return "Alerta{codigo=" + codigo
                + ", mensaje='" + mensaje + "'"
                + ", recurrente=" + esRecurrente
                + ", activacion=" + tiempoActivacion + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alerta)) return false;
        Alerta otra = (Alerta) o;
        return this.codigo == otra.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}