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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

public class FragmentDetalleReto extends Fragment {

    private Integer id;
    private String texto;
    private Integer contador;
    private Boolean superado;
    private static Integer idUsuario;
    private String nombreUsuario;
    private String fotoUsuario;
    private String premio;

    public FragmentDetalleReto() {
    }

    public static FragmentDetalleReto newInstance(TransferReto reto) {
        FragmentDetalleReto frgReto = new FragmentDetalleReto();

        Bundle arguments = new Bundle();

        arguments.putInt("id", reto.getId());
        arguments.putInt("usuario", reto.getUsuario().getId());
        arguments.putString("nombreUsuario", reto.getUsuario().getNombre());
        arguments.putString("fotoUsuario", reto.getUsuario().getAvatar());
        arguments.putString("texto", reto.getTexto());
        arguments.putInt("contador", reto.getContador());
        arguments.putBoolean("superado", reto.getSuperado());
        arguments.putString("premio", reto.getPremio());

        frgReto.setArguments(arguments);

        return frgReto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle != null) {
            id = bundle.getInt("id");
            texto = bundle.getString("texto");
            contador = bundle.getInt("contador");
            superado = bundle.getBoolean("superado");
            idUsuario = bundle.getInt("usuario");
            nombreUsuario = bundle.getString("nombreUsuario");
            fotoUsuario = bundle.getString("fotoUsuario");
            premio = bundle.getString("premio");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_reto, container, false);

        ((TextView) rootView.findViewById(R.id.reto)).setText("Reto de " + nombreUsuario);

        if(fotoUsuario != null && !fotoUsuario.equals(""))
            ((ImageView)rootView.findViewById(R.id.avatarR)).setImageBitmap(BitmapFactory.decodeFile(fotoUsuario));
        else
            ((ImageView)rootView.findViewById(R.id.avatarR)).setImageResource(R.drawable.avatar);

        ((TextView) rootView.findViewById(R.id.textoReto)).setText(texto);

        if(premio != null && !premio.equals(""))
            ((TextView) rootView.findViewById(R.id.textoPremio)).setText("Premio: " + premio);
        else
            ((TextView) rootView.findViewById(R.id.textoPremio)).setText("No tiene ningun premio asignado");

        //Si esta superado el texto debe ser diferente
        if (superado)
            ((TextView) rootView.findViewById(R.id.cont)).setText("RETO SUPERADO");
        else
            ((TextView) rootView.findViewById(R.id.cont)).setText("Ha respondido afirmativante "
                    + contador + " veces");
        ((TextView) rootView.findViewById(R.id.contador)).setText(contador + "/30");
        ((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgress(contador);


        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear(); //poner otro menu
        inflater.inflate(R.menu.menu_eliminar_reto_usuario, menu);
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
            case R.id.eliminarReto:
                DialogEliminarReto alertDialog = DialogEliminarReto.newInstance(id,texto, idUsuario);
                alertDialog.show(getActivity().getFragmentManager(), "Eliminar reto");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
