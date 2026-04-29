/**
 * 
 */
package es.ucm.as.integracion;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Evento {
    @DatabaseField(generatedId = true, columnName = "ID")
    private Integer id;

    @DatabaseField(columnName = "NOMBRE")
    private String nombreEvento;

    @DatabaseField(columnName = "FECHA", dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
    private Date fecha;

    @DatabaseField(columnName = "HORA_ALARMA", dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
    private Date horaAlarma;

    @DatabaseField(columnName = "HORA_EVENTO", dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
    private Date horaEvento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
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
}