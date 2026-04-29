package es.ucm.as.negocio.tutor;

import java.io.Serializable;

public class TransferTutor implements Serializable{

	static final long serialVersionUID = 1L;

	private Integer id;

	private String nombre;

	private String correo;

	private String codigoSincronizacion;

	private String contrasenha;

	private String respuesta;

	private String pregunta;

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

	public String getCodigoSincronizacion() {
		return codigoSincronizacion;
	}

	public void setCodigoSincronizacion(String codigoSincronizacion) {
		this.codigoSincronizacion = codigoSincronizacion;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
