package es.ucm.as.presentacion.vista.evento;

import android.content.Context;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;

public class AdaptadorEventos extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> nombresEventos = new ArrayList<String>();
    private ArrayList<String> fechasEventos = new ArrayList<String>();

    public AdaptadorEventos(Activity context) {
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
        View item = inflater.inflate(R.layout.row_eventos, null);

        TextView nombreTarea= (TextView)item.findViewById(R.id.listaEventoNombre);
        nombreTarea.setText(nombresEventos.get(position));
        TextView desc = (TextView)item.findViewById(R.id.listaEventosDes);
        desc.setText(fechasEventos.get(position));

        return(item);
    }

    public void setFechasEventos(ArrayList<String> fechasEvs) {
        this.fechasEventos = fechasEvs;
        notifyDataSetChanged();
    }

    public void setEventos(ArrayList<String> eventos) {
        this.nombresEventos = eventos;
        notifyDataSetChanged();
    }
}
