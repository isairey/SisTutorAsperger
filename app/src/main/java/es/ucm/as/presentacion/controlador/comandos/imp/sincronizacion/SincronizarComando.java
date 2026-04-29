package es.ucm.as.presentacion.controlador.comandos.imp.sincronizacion;

import java.util.ArrayList;
import java.util.List;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.suceso.TransferUsuarioEvento;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

public class SincronizarComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario usuarioSincro = saUsuario.consultarUsuario((Integer) datos);
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        TransferReto retoSincro= saSuceso.consultarReto(usuarioSincro.getId());

        ArrayList<TransferUsuarioEvento> eventosBDD = saUsuario.consultarEventosUsuario((Integer) datos);
        List<TransferEvento> eventosSincro = new ArrayList<>();

        for(int i=1; i < eventosBDD.size(); i++){
            TransferEvento e =  new TransferEvento(eventosBDD.get(i).getEvento().getId(), eventosBDD.get(i).getEvento().getNombre(), eventosBDD.get(i).getEvento().getFecha(),eventosBDD.get(i).getEvento().getHoraAlarma(), eventosBDD.get(i).getEvento().getHoraEvento());
            e.setAsistencia(eventosBDD.get(i).getAsistencia());
            eventosSincro.add(e);
        }

        ArrayList<TransferTarea> tareasSincro = saSuceso.consultarTareasHabilitadas((Integer) datos);

        Mensaje enviar = new Mensaje();
        enviar.setUsuario(usuarioSincro);
        enviar.setReto(retoSincro);
        enviar.setEventos(eventosSincro);
        enviar.setTareas(tareasSincro);

        ConectionManager conectionManager = new ConectionManager(enviar);
        String mensaje="VACIO";
        conectionManager.lanzarHebra();

        return mensaje;
    }
}
