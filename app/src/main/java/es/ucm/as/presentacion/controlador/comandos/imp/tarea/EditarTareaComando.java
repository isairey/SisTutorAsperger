package es.ucm.as.presentacion.controlador.comandos.imp.tarea;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

public class EditarTareaComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        saSuceso.editarTarea((TransferTarea) datos);
        return null;
    }
}
