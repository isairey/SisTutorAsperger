package es.ucm.as.presentacion.vista.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;


public class Decision extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Manager.getInstance().setActivity(this);
        Controlador.getInstancia().ejecutaComando(ListaComandos.EXISTE_TUTOR,null);
        finish();
    }
}
