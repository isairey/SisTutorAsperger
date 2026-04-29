package es.ucm.as.negocio.suceso;

import java.io.Serializable;

import es.ucm.as.negocio.usuario.TransferUsuario;

public class TransferUsuarioEvento implements Serializable {

    static final long serialVersionUID = 1L;
    private Integer id;
    private TransferEvento evento;
    private TransferUsuario usuario;
    private Integer activo;
    private String asistencia;

    public TransferUsuarioEvento(TransferEvento evento, TransferUsuario usuario) {
        this.evento = evento;
        this.usuario = usuario;
    }
    public TransferUsuarioEvento(){

    }

    public TransferEvento getEvento() {
        return evento;
    }

    public void setEvento(TransferEvento evento) {
        this.evento = evento;
    }

    public TransferUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TransferUsuario usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
