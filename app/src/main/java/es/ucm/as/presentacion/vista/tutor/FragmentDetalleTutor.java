package es.ucm.as.presentacion.vista.tutor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.tutor.TransferTutor;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.main.Manager;

public class FragmentDetalleTutor extends Fragment {

    private String codigoSincronizacion;
    private String nombre;
    private String email;
    private String preguntaSeguridad;
    private String respuestaSeguridad;
    private String contrasenha;
    private String codigo;
    private EditText nombrev;
    private EditText correov;
    private EditText preguntav;
    private EditText respuestav;

    public static FragmentDetalleTutor newInstance(TransferTutor tutor) {
        FragmentDetalleTutor frgTutor = new FragmentDetalleTutor();
        Bundle bundle = new Bundle();
        bundle.putSerializable("perfil_tutor", tutor);
        frgTutor.setArguments(bundle);
        return frgTutor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        TransferTutor tutor= (TransferTutor) bundle.getSerializable("perfil_tutor");
        codigoSincronizacion = tutor.getCodigoSincronizacion();
        nombre = tutor.getNombre();
        email = tutor.getCorreo();
        contrasenha = tutor.getContrasenha();
        preguntaSeguridad = tutor.getPregunta();
        respuestaSeguridad = tutor.getRespuesta();
        codigo = tutor.getCodigoSincronizacion();

        View rootView = inflater.inflate(R.layout.fragment_detalle_tutor, container, false);
        nombrev = (EditText)rootView.findViewById(R.id.nombreTutor);
        correov = (EditText)rootView.findViewById(R.id.correoTutor);
        preguntav = (EditText)rootView.findViewById(R.id.pregunta);
        respuestav = (EditText)rootView.findViewById(R.id.respuesta);

        nombrev.setText(nombre);
        correov.setText(email);
        preguntav.setText(preguntaSeguridad);
        respuestav.setText(respuestaSeguridad);

        Button aceptar = (Button) rootView.findViewById(R.id.aceptarTutor);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferTutor transfer = new TransferTutor();
                nombre = nombrev.getText().toString();
                email = correov.getText().toString();
                preguntaSeguridad = preguntav.getText().toString();
                respuestaSeguridad = respuestav.getText().toString();

                transfer.setNombre(nombre);
                transfer.setCorreo(email);
                transfer.setContrasenha(contrasenha);
                transfer.setPregunta(preguntaSeguridad);
                transfer.setRespuesta(respuestaSeguridad);
                transfer.setCodigoSincronizacion(codigo);

                Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_TUTOR,transfer);
                Toast toast = Toast.makeText(Manager.getInstance().getContext(),
                        "Cambios guardados correctamente", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button cancelar = (Button) rootView.findViewById(R.id.cancelarTutor);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombrev.setText(nombre);
                correov.setText(email);
                preguntav.setText(preguntaSeguridad);
                respuestav.setText(respuestaSeguridad);
            }
        });

        Button cambiarPassword = (Button) rootView.findViewById(R.id.password);
        cambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog a = DialogCambioContrasenha(inflater);
                a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                a.show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear(); //poner otro menu
        inflater.inflate(R.menu.menu_vacio, menu);
    }

    public AlertDialog DialogCambioContrasenha(LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = inflater.inflate(R.layout.dialog_cambio_password, null);

        final EditText pwd1 = (EditText) v.findViewById(R.id.pwd1);
        final EditText pwd2 = (EditText) v.findViewById(R.id.pwd2);
        builder.setView(v);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(pwd1.getText().toString().equals(pwd2.getText().toString())) {
                    contrasenha = pwd1.getText().toString();
                }else{
                    Toast toast = Toast.makeText(Manager.getInstance().getContext(),
                            "Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setTitle("Cambiar contraseña");
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return builder.create();

    }
}
