/**
 * 
 */
package es.ucm.as.integracion;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import es.ucm.as.negocio.utils.Frecuencia;

public class Tarea {

	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TEXTO_PREGUNTA")
	private String textoPregunta;

	@DatabaseField(columnName = "TEXTO_ALARMA")
	private String textoAlarma;

	@DatabaseField(columnName = "HORA_PREGUNTA", dataType = DataType.DATE_LONG, format = "dd/mm/yyyy HH:mm")
	private Date horaPregunta;

	@DatabaseField(columnName = "HORA_ALARMA", dataType = DataType.DATE_LONG, format = "dd/mm/yyyy HH:mm")
	private Date horaAlarma;

	@DatabaseField(columnName = "CONTADOR")
	private Integer contador;

	@DatabaseField(columnName = "FREC_TAREA", dataType = DataType.ENUM_STRING)
	private Frecuencia frecuenciaTarea;

	@DatabaseField(columnName = "MEJORAR")
	private Integer mejorar;

	@DatabaseField(columnName = "NUM_SI")
	private Integer numSi;

	@DatabaseField(columnName = "NUM_NO")
	private Integer numNo;

	@DatabaseField(columnName = "HABILITADA")
	private Boolean habilitada;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "USUARIO")
	private Usuario usuario;

	public Tarea(){
		contador = 0;
		numNo = 0;
		numSi = 0;
		habilitada = true;
		frecuenciaTarea = Frecuencia.DIARIA;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoAlarma() {
		return textoAlarma;
	}

	public void setTextoAlarma(String textoAlarma) {
		this.textoAlarma = textoAlarma;
	}

	public Date getHoraPregunta() {
		return horaPregunta;
	}

	public void setHoraPregunta(Date horaPregunta) {
		this.horaPregunta = horaPregunta;
	}

	public Date getHoraAlarma() {
		return horaAlarma;
	}

	public void setHoraAlarma(Date horaAlarma) {
		this.horaAlarma = horaAlarma;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Frecuencia getFrecuenciaTarea() {
		return frecuenciaTarea;
	}

	public void setFrecuenciaTarea(Frecuencia frecuenciaTarea) {
		this.frecuenciaTarea = frecuenciaTarea;
	}

	public Integer getMejorar() {
		return mejorar;
	}

	public void setMejorar(Integer mejorar) {
		this.mejorar = mejorar;
	}

	public Integer getNumSi() {
		return numSi;
	}

	public void setNumSi(Integer numSi) {
		this.numSi = numSi;
	}

	public Integer getNumNo() {
		return numNo;
	}

	public void setNumNo(Integer numNo) {
		this.numNo = numNo;
	}

	public Boolean getHabilitada() {
		return habilitada;
	}

	public void setHabilitada(Boolean habilitada) {
		this.habilitada = habilitada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


}