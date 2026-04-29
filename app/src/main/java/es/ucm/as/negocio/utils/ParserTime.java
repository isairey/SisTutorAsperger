package es.ucm.as.negocio.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ParserTime {

    // Forma el string a partir de dos enteros: la hora y el minuto
    public static String toString(Integer hour, Integer min){
        return hour.toString() + ":" + min.toString();
    }

    // Devuelve la hora
    public static Integer hour(String time){
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]);
    }

    // Devuelve el minuto
    public static Integer min(String time){
        String[] parts = time.split(":");
        return Integer.parseInt(parts[1]);
    }

    // Se crea el objeto a partir del numero de milisegundos desde el 1/1/1970 pero a nosotros
    // solo nos interesa la hora, no la fecha ya que ni la almacenamos en la BBDD
    public static Date toDate(String time){
        Integer hour = hour(time);
        Integer min = min(time);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
        Date d = c.getTime();
        return d;
    }

    public static String DateToString(Date date){
        String ret = date.toString().substring(10, 16);
        return ret;
    }
}
