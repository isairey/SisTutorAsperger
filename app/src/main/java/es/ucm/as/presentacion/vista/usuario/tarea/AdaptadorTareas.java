package es.ucm.as.presentacion.vista.usuario.tarea;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;

/**
 * Esta clase se corresponde con una fila de la tabla de tareas que tiene cada usuario
 */
public class AdaptadorTareas extends BaseAdapter {

    private Context context;
    private ArrayList<String> textosAlarma;
    private ArrayList<String> horasAlarma;
    private ArrayList<String> textosPreguntas;
    private ArrayList<String> horasPregunta;
    private ArrayList<Integer> si;
    private ArrayList<Integer> no;
    private ArrayList<Integer> total;
    private ArrayList<String> frecuencias;
    private ArrayList<Integer> habilitadas;
    private LayoutInflater inflater;

    public AdaptadorTareas(ArrayList<String> textosAlarma, ArrayList<String> horasAlarma,
                           ArrayList<String> textosPreguntas, ArrayList<String> horasPregunta,
                           ArrayList<Integer> si,ArrayList<Integer> no, ArrayList<String> frecuencias,
                           ArrayList<Integer> habilitadas, Context context) {
        this.context = context;
        this.textosAlarma = textosAlarma;
        this.horasAlarma = horasAlarma;
        this.textosPreguntas = textosPreguntas;
        this.horasPregunta = horasPregunta;
        this.si = si;
        this.no = no;
        this.frecuencias = frecuencias;
        this.habilitadas = habilitadas;
        this.total = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        return textosAlarma.size();
    }

    @Override
    public Object getItem(int position) {
        return textosAlarma.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtTitle;      // texto alarma
        TextView horaAlarma;    // hora alarma
        TextView txtPregunta;   // texto pregunta
        TextView horaPregunta;  // hora pregunta
        TextView sit;           // numero de si
        TextView not;           // numero de no
        TextView total;         // balance del progreso (si - no = total)
        TextView frecuencia;    // frecuencia
        CheckBox habilitada;    // true si esta habilitada

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.tareas_row, parent, false);

        // Locacalizar en el listview_item.xml
        txtTitle = (TextView) itemView.findViewById(R.id.nombreTarea);
        horaAlarma = (TextView) itemView.findViewById(R.id.horaAlarma);
        txtPregunta = (TextView) itemView.findViewById(R.id.preguntaTarea);
        horaPregunta = (TextView) itemView.findViewById(R.id.horaPregunta);
        sit = (TextView) itemView.findViewById(R.id.si);
        not = (TextView) itemView.findViewById(R.id.no);
        total = (TextView) itemView.findViewById(R.id.total);
        frecuencia = (TextView) itemView.findViewById(R.id.frecuencia);
        habilitada = (CheckBox) itemView.findViewById(R.id.habilitada);

        // Dar valores a cada columna de la fila
        txtTitle.setText(textosAlarma.get(position));
        horaAlarma.setText(horasAlarma.get(position));
        txtPregunta.setText(textosPreguntas.get(position));
        horaPregunta.setText(horasPregunta.get(position));
        String ss = si.get(position).toString();
        sit.setText(ss);
        String sn = no.get(position).toString();
        not.setText(sn);
        Integer t = si.get(position)-no.get(position);
        if(t >= 0)
            total.setTextColor(Color.GREEN);
        else
            total.setTextColor(Color.RED);
        total.setText(t.toString());
        frecuencia.setText(frecuencias.get(position));
        if (habilitadas.get(position) == 1)
            habilitada.setChecked(true);
        else
            habilitada.setChecked(false);

        // Poner el texto en color negro
        txtTitle.setTextColor(Color.BLACK);
        horaAlarma.setTextColor(Color.BLACK);
        txtPregunta.setTextColor(Color.BLACK);
        horaPregunta.setTextColor(Color.BLACK);
        sit.setTextColor(Color.BLACK);
        not.setTextColor(Color.BLACK);
        frecuencia.setTextColor(Color.BLACK);
        habilitada.setTextColor(Color.BLACK);

        return itemView;
    }
}
