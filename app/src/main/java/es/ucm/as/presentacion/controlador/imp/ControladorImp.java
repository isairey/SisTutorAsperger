package es.ucm.as.presentacion.controlador.imp;


import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.Dispatcher;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as.presentacion.controlador.comandos.factoria.FactoriaComandos;

public class ControladorImp extends Controlador {
    @Override
    public void ejecutaComando(String accion, Object datos) {
        Command comando = FactoriaComandos.getInstancia().getCommand(accion);
        Object ret;
        try {
            ret = comando.ejecutaComando(datos);
            actualizaVista(accion,ret);
        } catch (commandException e) {
            // TODO Auto-generated catch block
        }
    }

    @Override
    public void actualizaVista(String accion, Object datos) {
        Dispatcher.getInstancia().dispatch(accion, datos);
    }
}
