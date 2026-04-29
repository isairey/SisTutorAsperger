package es.ucm.as.presentacion.vista.usuario;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

public class DialogEliminarUsuario extends DialogFragment {

    public static DialogEliminarUsuario newInstance(int idUsuario, String nombreUsuario) {
        DialogEliminarUsuario frag = new DialogEliminarUsuario();
        Bundle args = new Bundle();
        args.putInt("idUsuario", idUsuario);
        args.putString("nombreUsuario", nombreUsuario);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // .setIcon(R.drawable.androidhappy)
                .setTitle("Eliminar Usuario")
                .setMessage("¿Desea eliminar el usuario: " + getArguments().getString("nombreUsuario")+"?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Controlador.getInstancia().ejecutaComando(ListaComandos.ELIMINAR_USUARIO,
                                getArguments().getInt("idUsuario"));
                        Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_USUARIOS,null);
                        Toast.makeText(getActivity(), "El usuario "
                                + getArguments().getString("nombreUsuario")
                                + " ha sido eliminado con éxito", Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
