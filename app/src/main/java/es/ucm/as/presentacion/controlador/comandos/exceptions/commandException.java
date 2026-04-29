package es.ucm.as.presentacion.controlador.comandos.exceptions;

public class commandException extends Exception {
    public commandException(String msg) {
        super(msg);
    }
    public commandException() {
        super();
    }
}
