package es.ucm.as.negocio.usuario;

import java.util.ArrayList;

import es.ucm.as.negocio.suceso.TransferUsuarioEvento;

public interface SAUsuario {

	public void crearUsuario(TransferUsuario transferUsuario);

	public void editarUsuario(TransferUsuario usuarioMod);

	public void eliminarUsuario(Integer idUsuario);

	public TransferUsuario consultarUsuario(Integer idUsuario);

	public void cargarTareasBBDD(Integer idUsuario);

	public ArrayList<TransferUsuario> consultarUsuarios();

	public ArrayList<TransferUsuarioEvento> consultarEventosUsuario(Integer idUsuario);

	public void guardarEventosUsuario(ArrayList<TransferUsuarioEvento> eventosUsuario);

	public void enviarCorreo(Integer idUsuario);

	public void generarPDF(Integer idUsuario);

	public void actualizarPuntuacion(TransferUsuario actualizaPuntuacion);
}