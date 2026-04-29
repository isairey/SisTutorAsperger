package es.ucm.as.presentacion.vista.usuario.evento;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;

public class AdaptadorUsuarioEventos extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> nombresEventos = new ArrayList<String>();
    private ArrayList<String> asistencia = new ArrayList<String>();

    public AdaptadorUsuarioEventos(Activity context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombresEventos.size();
    }

    @Override
    public Object getItem(int position) {
        return nombresEventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = inflater.inflate(R.layout.row_usuario_eventos, null);

        TextView nombreTarea= (TextView)item.findViewById(R.id.evento);
        nombreTarea.setText(nombresEventos.get(position));
        TextView desc = (TextView)item.findViewById(R.id.asistencia);
        desc.setText(asistencia.get(position));

        return(item);
    }

    public void setAsistencia(ArrayList<String> asistencia) {
        this.asistencia = asistencia;
        notifyDataSetChanged();
    }

    public void setEventos(ArrayList<String> eventos) {
        this.nombresEventos = eventos;
        notifyDataSetChanged();
    }
}
