package es.ucm.as.presentacion.controlador.comandos;

import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

public interface Command {
    Object ejecutaComando(Object datos) throws commandException;
}
