package es.ucm.as.negocio.suceso;

import java.io.Serializable;
import java.util.Date;

public class TransferEvento implements Serializable {

    static final long serialVersionUID = 1L;
    private Integer id;
    private String nombre;
    private Date fecha;
    private Date horaAlarma;
    private Date horaEvento;
    private String asistencia;

    public TransferEvento(){}
    public TransferEvento(Integer id, String nombre, Date fecha, Date horaAlarma, Date horaEvento) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.horaAlarma = horaAlarma;
        this.horaEvento = horaEvento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraAlarma() {
        return horaAlarma;
    }

    public void setHoraAlarma(Date horaAlarma) {
        this.horaAlarma = horaAlarma;
    }

    public Date getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(Date horaEvento) {
        this.horaEvento = horaEvento;
    }
    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
