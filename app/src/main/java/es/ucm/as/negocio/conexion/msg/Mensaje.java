package es.ucm.as.negocio.conexion.msg;

import java.io.Serializable;
import java.util.List;

import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.TransferUsuario;

//Objeto que se envía a traves de los sockets(sincronización)
public class Mensaje implements Serializable{

    static final long serialVersionUID = 1L;

    private String verificar;
    private TransferUsuario usuario;
    private TransferReto reto;
    private List<TransferEvento> eventos;
    private List<TransferTarea> tareas;

    public Mensaje(String verificar){
        this.verificar = verificar;
    }

    public Mensaje(){
        this.verificar = "";
        this.usuario = new TransferUsuario();
    }

    public Mensaje(TransferUsuario usuario, TransferReto reto,
                   List<TransferEvento> eventos, List<TransferTarea> tareas ){
        this.verificar = "";
        this.usuario = usuario;
        this.reto = reto;
        this.eventos = eventos;
        this.tareas = tareas;
    }

    public TransferUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TransferUsuario usuario) {
        this.usuario = usuario;
    }

    public TransferReto getReto() {
        return reto;
    }

    public void setReto(TransferReto reto) {
        this.reto = reto;
    }

    public List<TransferEvento> getEventos() {
        return eventos;
    }

    public void setEventos(List<TransferEvento> eventos) {
        this.eventos = eventos;
    }

    public List<TransferTarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<TransferTarea> tareas) {
        this.tareas = tareas;
    }

    public String getVerificar() {
        return verificar;
    }

    public void setVerificar(String verificar) {
        this.verificar = verificar;
    }
}