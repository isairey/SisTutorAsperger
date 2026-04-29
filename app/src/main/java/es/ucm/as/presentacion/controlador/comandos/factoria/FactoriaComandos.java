package es.ucm.as.presentacion.controlador.comandos.factoria;


import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.factoria.imp.FactoriaComandosImp;

/**
 * Created by Jeffer on 02/03/2016.
 */
public abstract class FactoriaComandos {
    private static FactoriaComandos instancia;

    public static FactoriaComandos getInstancia() {

        if (instancia == null){
            instancia = new FactoriaComandosImp();
        }
        return instancia;
    }
    public abstract Command getCommand(String comando);
}
