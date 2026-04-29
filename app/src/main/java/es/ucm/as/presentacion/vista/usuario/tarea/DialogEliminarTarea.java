package es.ucm.as.presentacion.vista.usuario.tarea;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;


public class DialogEliminarTarea extends DialogFragment {

    public static DialogEliminarTarea newInstance(String tarea, Integer idTarea, Integer idUsuario) {
        DialogEliminarTarea dialog = new DialogEliminarTarea();
        Bundle args = new Bundle();
        args.putString("tarea", tarea);
        args.putInt("idTarea", idTarea);
        args.putInt("idUsuario", idUsuario);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("tarea"))
                .setMessage("¿Desea eliminar esta tarea?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Controlador.getInstancia().ejecutaComando(ListaComandos.ELIMINAR_TAREA, getArguments().getInt("idTarea"));
                        Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TAREAS, getArguments().getInt("idUsuario"));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
