package es.ucm.as.negocio.suceso;

import java.io.Serializable;

import es.ucm.as.negocio.usuario.TransferUsuario;

public class TransferReto implements Serializable {

	static final long serialVersionUID = 1L;

	private Integer id;

	private TransferUsuario usuario;

	private Integer contador;

	private String texto;

	private boolean superado;

	private String premio;

	public TransferReto() {
	}

	public TransferReto(Integer id, TransferUsuario usuario, Integer contador, String texto, Boolean superado, String premio) {
		this.id = id;
		this.usuario = usuario;
		this.contador = contador;
		this.texto = texto;
		this.superado = superado;
		this.premio = premio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TransferUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TransferUsuario usuario) {
		this.usuario = usuario;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Boolean getSuperado() {
		return superado;
	}

	public void setSuperado(Boolean superado) {
		this.superado = superado;
	}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}
}