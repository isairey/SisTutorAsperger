package es.ucm.as.presentacion.controlador.comandos.imp.tutor;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.tutor.SATutor;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

public class ConsultarTutorComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SATutor saTutor = FactoriaSA.getInstancia().nuevoSATutor();
        return  saTutor.consultarTutor();
    }
}
