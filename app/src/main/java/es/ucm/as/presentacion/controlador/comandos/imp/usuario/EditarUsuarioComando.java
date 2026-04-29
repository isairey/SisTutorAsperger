package es.ucm.as.presentacion.controlador.comandos.imp.usuario;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

public class EditarUsuarioComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        saUsuario.editarUsuario((TransferUsuario) datos);
        return null;
    }
}
