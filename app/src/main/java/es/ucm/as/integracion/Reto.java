/**
 * 
 */
package es.ucm.as.integracion;

import com.j256.ormlite.field.DatabaseField;


public class Reto {

	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "USUARIO")
	private Usuario usuario;

	@DatabaseField(columnName = "CONTADOR")
	private Integer contador;

	@DatabaseField(columnName = "TEXTO")
	private String texto;

	@DatabaseField(columnName = "SUPERADO")
	private Boolean superado;

	@DatabaseField(columnName = "PREMIO")
	private String premio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
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