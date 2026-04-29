package es.ucm.as.presentacion.controlador.comandos.imp.evento;

import java.util.ArrayList;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;


public class CrearEventoConsultarUsuarios implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario sa = FactoriaSA.getInstancia().nuevoSAUsuario();
        ArrayList<TransferUsuario> ret = sa.consultarUsuarios();
        return ret;
    }
}
