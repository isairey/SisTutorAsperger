package es.ucm.as.presentacion.vista.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.tutor.TransferTutor;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

public class Acceso extends AppCompatActivity {

    private static String password;
    private static String preguntaSeguridad;
    private static String respuestaSeguridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
        Intent i = getIntent();
        TransferTutor tutor = (TransferTutor) i.getSerializableExtra("tutor");
        password = tutor.getContrasenha();
        preguntaSeguridad = tutor.getPregunta();
        respuestaSeguridad = tutor.getRespuesta();
        AlertDialog a = dialogVerificarAcceso(getLayoutInflater());
        a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        a.setCancelable(false);
        a.setCanceledOnTouchOutside(false);
        a.show();
    }

    public AlertDialog dialogVerificarAcceso(final LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.dialog_password, null);
        final EditText pwd1 = (EditText) v.findViewById(R.id.pwd1);
        final TextView recuperarPassword = (TextView) v.findViewById(R.id.recuperarPassword);
        recuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog a = dialogRecuperarPassword(inflater);
                a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                a.show();
            }
        });

        builder.setView(v);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(password != null && password.equals(pwd1.getText().toString())) {
                    Intent intent = new Intent( Manager.getInstance().getContext(), MainActivity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Manager.getInstance().getContext().startActivity(intent);
                }else{
                    Toast errorNombre =
                            Toast.makeText(Manager.getInstance().getContext(),
                                    "Clave incorrecta", Toast.LENGTH_SHORT);
                    errorNombre.show();
                    Controlador.getInstancia().ejecutaComando(ListaComandos.ACCESO, null);
                }
            }
        });

        builder.setTitle("Clave de acceso");
        builder.setIcon(R.drawable.logo);
        return builder.create();
    }


    public AlertDialog dialogRecuperarPassword(LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.dialog_password_restaurar, null);

        final TextView question = (TextView) v.findViewById(R.id.pregunta);
        question.setText(this.preguntaSeguridad);
        final EditText answer = (EditText) v.findViewById(R.id.respuesta);

        builder.setView(v);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    if (answer.getText().toString().equals(respuestaSeguridad))
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    else {
                        Toast errorNombre =
                                Toast.makeText(getApplicationContext(),
                                        "Respuesta incorrecta", Toast.LENGTH_SHORT);
                        errorNombre.show();
                        Controlador.getInstancia().ejecutaComando(ListaComandos.ACCESO, null);
                    }
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        builder.setTitle("Recuperar contraseña");
        builder.setIcon(R.drawable.logo);
        return builder.create();
    }
}
