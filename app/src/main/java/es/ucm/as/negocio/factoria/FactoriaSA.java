package es.ucm.as.negocio.factoria;

import es.ucm.as.negocio.factoria.imp.FactoriaSAImp;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.tutor.SATutor;
import es.ucm.as.negocio.usuario.SAUsuario;

public abstract class FactoriaSA {

	private static FactoriaSA instancia;

	public abstract SATutor nuevoSATutor();

	public abstract SAUsuario nuevoSAUsuario();

	public abstract SASuceso nuevoSASuceso();

	public static FactoriaSA getInstancia() {
		if (instancia == null)
            instancia = new FactoriaSAImp();
        return instancia;
	}
}