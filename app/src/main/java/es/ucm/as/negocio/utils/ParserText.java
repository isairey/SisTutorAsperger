package es.ucm.as.negocio.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.ucm.as.R;
import es.ucm.as.integracion.Tarea;
import es.ucm.as.presentacion.vista.main.Manager;

public class ParserText {

    private ArrayList<Tarea> tareas;

    public ParserText(){
        tareas = new ArrayList<>();
    }

    public ArrayList<Tarea> getTareas(){
        return this.tareas;
    }

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readTareas(Perfil tipo) {
        String alarma = "";
        String pregunta;
        String horaPregunta;
        String horaAlarma;
        try {
            // Si no se ha seleccionado perfil se pone por defecto el A
            Integer archivo = R.raw.perfil_estandar;
            if(tipo == Perfil.Chica)
                archivo = R.raw.perfil_chica;


            InputStream fraw = Manager.getInstance().getContext().getResources().openRawResource(archivo);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                horaAlarma = brin.readLine();
                pregunta = brin.readLine();
                horaPregunta = brin.readLine();
                if (alarma != null && horaAlarma != null && pregunta != null && horaPregunta != null)
                        tareas.add(toTarea(alarma, pregunta, horaAlarma, horaPregunta));

            }
            fraw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*A partir de dos Strings crea una pregunta con esos textos de alarma y pregunta*/
    public Tarea toTarea(String textoAlarma, String textoPregunta, String horaAlarma, String horaPregunta){
        Tarea ret = new Tarea();
        SimpleDateFormat horasMinutos = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat diaMes = new SimpleDateFormat("dd/MM/yyyy");
        ret.setTextoAlarma(textoAlarma);
        ret.setTextoPregunta(textoPregunta);
        ret.setFrecuenciaTarea(Frecuencia.DIARIA);
        ret.setMejorar(30);
        try {
            Date e = Calendar.getInstance().getTime();
            String diaactual = diaMes.format(e).toString();
            String fechafinal= diaactual+" "+horaAlarma;
            ret.setHoraAlarma(horasMinutos.parse(fechafinal));
            fechafinal= diaactual+" "+horaPregunta;
            ret.setHoraPregunta(horasMinutos.parse(fechafinal));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
