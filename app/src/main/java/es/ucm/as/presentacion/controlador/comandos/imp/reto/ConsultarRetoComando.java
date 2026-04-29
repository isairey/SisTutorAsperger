package es.ucm.as.presentacion.controlador.comandos.imp.reto;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

public class ConsultarRetoComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        return saSuceso.consultarReto((Integer) datos);
    }
}
