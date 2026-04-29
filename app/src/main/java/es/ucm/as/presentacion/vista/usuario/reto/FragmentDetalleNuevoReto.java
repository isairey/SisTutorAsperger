package es.ucm.as.presentacion.vista.usuario.reto;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

public class FragmentDetalleNuevoReto extends Fragment {

    private EditText textoReto;
    private EditText premio;
    private Button anadirNuevoReto;
    private Button cancelarNuevoReto;

    private Integer idUsuario;
    private String nombreUsuario;
    private String fotoUsuario;

    public static FragmentDetalleNuevoReto newInstance(TransferReto reto) {
        FragmentDetalleNuevoReto frgNuevoReto = new FragmentDetalleNuevoReto();

        Bundle arguments = new Bundle();
        if(reto.getUsuario().getId() != null) {
            arguments.putInt("usuario", reto.getUsuario().getId());
            arguments.putString("nombreUsuario", reto.getUsuario().getNombre());
            arguments.putString("fotoUsuario", reto.getUsuario().getAvatar());
        }
        frgNuevoReto.setArguments(arguments);

        return frgNuevoReto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle != null) {
            idUsuario = bundle.getInt("usuario");
            nombreUsuario = bundle.getString("nombreUsuario");
            fotoUsuario = bundle.getString("fotoUsuario");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_nuevo_reto, container, false);

        ((TextView) rootView.findViewById(R.id.retoNuevo)).setText("Reto de " + nombreUsuario);

        if(fotoUsuario != null && !fotoUsuario.equals(""))
            ((ImageView)rootView.findViewById(R.id.avatarNR)).setImageBitmap(BitmapFactory.decodeFile(fotoUsuario));
        else
            ((ImageView)rootView.findViewById(R.id.avatarNR)).setImageResource(R.drawable.avatar);

        textoReto = (EditText) rootView.findViewById(R.id.textoReto);
        premio = (EditText) rootView.findViewById(R.id.premio);
        anadirNuevoReto = (Button) rootView.findViewById(R.id.botonAceptarReto);
        cancelarNuevoReto = (Button) rootView.findViewById(R.id.botonCancelarReto);

        anadirNuevoReto.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comando guarda reto al usuario
                if(!textoReto.getText().toString().matches("")){
                    TransferReto reto = new TransferReto();
                    reto.setTexto(textoReto.getText().toString());
                    reto.setContador(0);
                    TransferUsuario t_usuario = new TransferUsuario();
                    t_usuario.setId(idUsuario);
                    reto.setUsuario(t_usuario);
                    reto.setPremio(premio.getText().toString());
                    reto.setSuperado(false);
                    Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_RETO, reto);
                    Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_RETO, idUsuario);
                    Toast.makeText(getActivity(), "El reto " + textoReto.getText().toString()
                            + " ha sido creado con éxito", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(), "Es necesario introducir un texto para el reto",
                            Toast.LENGTH_SHORT).show();
            }
        });

        cancelarNuevoReto.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, idUsuario);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear(); //poner otro menu
        inflater.inflate(R.menu.menu_sucesos_usuario, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.usuario:
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, idUsuario);
                break;
            case R.id.tareasUsuario:
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TAREAS, idUsuario);
                break;
            case R.id.retoUsuario:
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_RETO, idUsuario);
                break;
            case R.id.eventosUsuario:
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_EVENTOS_USUARIO,idUsuario);
                break;
            case R.id.enviarCorreo:
                Controlador.getInstancia().ejecutaComando(ListaComandos.GENERAR_PDF, idUsuario);
                Controlador.getInstancia().ejecutaComando(ListaComandos.ENVIAR_CORREO, idUsuario);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}