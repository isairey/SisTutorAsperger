
package es.ucm.as.integracion;

import com.j256.ormlite.field.DatabaseField;

public class Tutor {

    @DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

    @DatabaseField(columnName = "NOMBRE")
	private String nombre;

    @DatabaseField(columnName = "CORREO")
	private String correo;

    @DatabaseField(columnName = "CODIGO")
	private String codigoSincronizacion;

    @DatabaseField(columnName = "CONTRASENHA")
    private String contrasenha;

    @DatabaseField(columnName = "PREGUNTA")
    private String respuesta;

    @DatabaseField(columnName = "RESPUESTA")
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
