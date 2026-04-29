package es.ucm.as.presentacion.vista.usuario;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;


class AdaptadorUsuarios extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<String> nombres = new ArrayList<String>();
        private ArrayList<String> avatares = new ArrayList<String>();

    public AdaptadorUsuarios(Activity context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombres.size();
    }

    @Override
    public Object getItem(int position) {
        return nombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = inflater.inflate(R.layout.row_usuarios, parent, false);

        TextView nombreUser= (TextView)item.findViewById(R.id.nombreUsuarioLista);
        nombreUser.setText(nombres.get(position));
        ImageView img = (ImageView)item.findViewById(R.id.avatarUsuarioLista);
        if(!avatares.get(position).equals(""))
            img.setImageBitmap(BitmapFactory.decodeFile(avatares.get(position)));
        else
            img.setImageResource(R.drawable.avatar);

        return(item);
    }

    public void setNombres(ArrayList<String> nombres) {
        this.nombres = nombres;
        notifyDataSetChanged();
    }

    public void setAvatares(ArrayList<String> imagenes) {
        this.avatares = imagenes;
        notifyDataSetChanged();
    }
}
