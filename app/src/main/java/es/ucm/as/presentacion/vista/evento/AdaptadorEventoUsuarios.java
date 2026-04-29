package es.ucm.as.presentacion.vista.evento;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;

public class AdaptadorEventoUsuarios  extends BaseAdapter {

    private ArrayList<String> nombresUsuarios = new ArrayList<String>();
    private ArrayList<Integer> idsUsuarios = new ArrayList<Integer>();
    private ArrayList<String> usuariosAsistencia;
    private ArrayList<Boolean> usuariosActivos = new ArrayList<Boolean>();
    private LayoutInflater inflater;

    public AdaptadorEventoUsuarios(Activity context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item, boolean activado, int i) {
        nombresUsuarios.add(item);
        usuariosActivos.add(i,activado);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return nombresUsuarios.size();
    }

    @Override
    public String getItem(int position) {
        return nombresUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.row_evento_listado_usuarios, null);
        final ViewHolder holder = new ViewHolder();
        holder.chkItem = (CheckBox)convertView.findViewById(R.id.chkItem);
        TextView nombreUsuario = (TextView) convertView.findViewById(R.id.eventoNombreUsuario);
        TextView usuarioAsistencia = (TextView) convertView.findViewById(R.id.eventoUsuarioAsistira);

        holder.chkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                usuariosActivos.set(position, holder.chkItem.isChecked());
            }
        });
        holder.chkItem.setChecked(usuariosActivos.get(position));
        convertView.setTag(holder);
        nombreUsuario.setText(getItem(position));
        if(usuariosAsistencia != null)
            usuarioAsistencia.setText(usuariosAsistencia.get(position));
        else//Significa que estamos creando un nuevo evento
            usuarioAsistencia.setText("-");

        return convertView;
    }

    public int getItemsLength() {
        if(usuariosActivos == null) return 0;
        return usuariosActivos.size();
    }

    public void cambiaCheck(int i) {
        usuariosActivos.set(i,!usuariosActivos.get(i));
        notifyDataSetChanged();
    }

    public void setDatos(ArrayList<String> usuarioEventos) {
        nombresUsuarios=usuarioEventos;
        notifyDataSetChanged();
    }

    public void setDatosCheck(ArrayList<Boolean> activos) {
        usuariosActivos=activos;
        notifyDataSetChanged();
    }

    public void setDatosAsistencia(ArrayList<String> asistencia) {
        usuariosAsistencia=asistencia;
        notifyDataSetChanged();
    }
    public ArrayList<String> getAsistencia(){
        return usuariosAsistencia;
    }

    public void setDatosIds(ArrayList<Integer> idsUsers) {
        idsUsuarios = idsUsers;
    }
    public ArrayList<Integer> getDatosIds(){
        return idsUsuarios;
    }

    public ArrayList<Boolean> getDatosCheck() {
        return usuariosActivos;
    }

    public static class ViewHolder {
            public CheckBox chkItem;
        }
}

