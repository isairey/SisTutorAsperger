package es.ucm.as.negocio.usuario;

import java.io.Serializable;

public class TransferUsuario implements Serializable{

	static final long serialVersionUID = 1L;

	private Integer id;

	private String nombre;

	private String correo;

	private String avatar;

	private String telefono;

	private Integer puntuacion;

	private Integer puntuacionAnterior;

	private String curso;

	private String dni;

	private String direccion;

	private String tipoPerfil;

	private String notas;

	private String nombrePadre;

	private String nombreMadre;

	private String correoPadre;

	private String correoMadre;

	private String telPadre;

	private String telMadre;

	private String centroAcademico;

	private String codigoSincronizacion;

	//Informacion para el listado


	public TransferUsuario(Integer id, String nombre, String avatar) {
		this.id = id;
		this.nombre = nombre;
		this.avatar = avatar;
	}

	//Vacio
	public TransferUsuario(){	}

	//Toda la informacion para el fragment detalle
	public TransferUsuario(Integer id, String nombre, String correo, String avatar, String telefono,
						   Integer puntuacion, Integer puntuacionAnterior, String curso, String dni,
						   String direccion, String tipoPerfil, String notas, String nombrePadre,
						   String nombreMadre, String correoPadre, String correoMadre,
						   String telPadre, String telMadre, String centroAcademico,
						   String codigoSincronizacion) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.avatar = avatar;
		this.telefono = telefono;
		this.puntuacion = puntuacion;
		this.puntuacionAnterior = puntuacionAnterior;
		this.curso = curso;
		this.dni = dni;
		this.direccion = direccion;
		this.tipoPerfil = tipoPerfil;
		this.notas = notas;
		this.nombrePadre = nombrePadre;
		this.nombreMadre = nombreMadre;
		this.correoPadre = correoPadre;
		this.correoMadre = correoMadre;
		this.telMadre = telMadre;
		this.telPadre = telPadre;
		this.centroAcademico = centroAcademico;
		this.codigoSincronizacion = codigoSincronizacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Integer getPuntuacionAnterior() {
		return puntuacionAnterior;
	}

	public void setPuntuacionAnterior(Integer puntuacionAnterior) {
		this.puntuacionAnterior = puntuacionAnterior;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(String tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getNombrePadre() {
		return nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public String getNombreMadre() {
		return nombreMadre;
	}

	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}

	public String getCorreoPadre() {
		return correoPadre;
	}

	public void setCorreoPadre(String correoPadre) {
		this.correoPadre = correoPadre;
	}

	public String getCorreoMadre() {
		return correoMadre;
	}

	public void setCorreoMadre(String correoMadre) {
		this.correoMadre = correoMadre;
	}

	public String getTelMadre() {
		return telMadre;
	}

	public void setTelMadre(String telMadre) {
		this.telMadre = telMadre;
	}

	public String getTelPadre() {
		return telPadre;
	}

	public void setTelPadre(String telPadre) {
		this.telPadre = telPadre;
	}

	public String getCentroAcademico() {
		return centroAcademico;
	}

	public void setCentroAcademico(String centroAcademico) {
		this.centroAcademico = centroAcademico;
	}

	public String getCodigoSincronizacion() {
		return codigoSincronizacion;
	}

	public void setCodigoSincronizacion(String codigoSincronizacion) {
		this.codigoSincronizacion = codigoSincronizacion;
	}
}