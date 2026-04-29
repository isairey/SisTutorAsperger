package es.ucm.as.negocio.suceso;

import java.io.Serializable;
import java.util.Date;

import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.negocio.utils.Frecuencia;

public class TransferTarea implements Serializable {

	static final long serialVersionUID = 1L;

	private Integer id;

	private String textoPregunta;

	private String textoAlarma;

	private Date horaPregunta;

	private Date horaAlarma;

	private Integer contador;

	private Frecuencia frecuenciaTarea;

	private Integer mejorar;

	private Integer numSi;

	private Integer numNo;

	private Boolean habilitada;

	private TransferUsuario usuario;

	public TransferTarea(){
		contador = 0;
		numNo = 0;
		numSi = 0;
		habilitada = true;
		frecuenciaTarea = Frecuencia.DIARIA;
	}

    // Constructor sin id, adecuado para crear tareas
    public TransferTarea(String textoAlarma, Date horaAlarma,
						 String textoPregunta, Date horaPregunta, Integer mejorar, TransferUsuario usuario){
        this.textoAlarma = textoAlarma;
        this.horaAlarma = horaAlarma;
        this.textoPregunta = textoPregunta;
        this.horaPregunta = horaPregunta;
        this.mejorar = mejorar;
		this.usuario = usuario;
    }

    // Constructor con id, adecuado para modificar tareas
    public TransferTarea(Integer id, String textoAlarma, Date horaAlarma,
						 String textoPregunta, Date horaPregunta, Integer mejorar, TransferUsuario usuario,
						 Integer contador, Frecuencia frecuencia, Integer numSi, Integer numNo,
						 Boolean habilitada){
        this.id = id;
        this.textoAlarma = textoAlarma;
        this.horaAlarma = horaAlarma;
        this.textoPregunta = textoPregunta;
        this.horaPregunta = horaPregunta;
        this.mejorar = mejorar;
        this.contador = contador;
        this.frecuenciaTarea = frecuencia;
        this.numSi = numSi;
        this.numNo = numNo;
        this.habilitada = habilitada;
		this.usuario = usuario;
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

	public TransferUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TransferUsuario usuario) {
		this.usuario = usuario;
	}


}