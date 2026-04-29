package es.ucm.as.presentacion.vista.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.ucm.as.R;
import es.ucm.as.presentacion.vista.usuario.FragmentDetalleUsuario;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        FragmentDetalleUsuario detalle =
                (FragmentDetalleUsuario)getSupportFragmentManager()
                        .findFragmentById(R.id.FrgDetalle);
    }
}
