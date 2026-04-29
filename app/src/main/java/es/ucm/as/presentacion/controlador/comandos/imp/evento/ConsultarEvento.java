package es.ucm.as.presentacion.controlador.comandos.imp.evento;

import java.util.ArrayList;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferUsuarioEvento;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;


public class ConsultarEvento implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso sa = FactoriaSA.getInstancia().nuevoSASuceso();
        ArrayList<TransferUsuarioEvento> ret = sa.consultarUsuariosEvento((TransferEvento) datos);
        return ret;
    }
}
