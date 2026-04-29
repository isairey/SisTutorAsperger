package es.ucm.as.integracion;

import com.j256.ormlite.field.DatabaseField;


public class UsuarioEvento {

    @DatabaseField(generatedId = true, columnName = "ID")
    private Integer id;
    @DatabaseField(foreign = true,foreignAutoRefresh = true, columnName = "USUARIO")
    private Usuario usuario;
    @DatabaseField(foreign = true, foreignAutoRefresh = true,columnName = "EVENTO")
    private Evento evento;
    @DatabaseField(columnName = "ASISTENCIA")
    private String asistencia;


    public UsuarioEvento(Evento evento, Usuario usuario) {
        this.usuario = usuario;
        this.evento = evento;
    }
    public UsuarioEvento(){} // ORMLITE

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
