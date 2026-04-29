package es.ucm.as.presentacion.vista.usuario.evento;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

public class FragmentDetalleUsuarioEvento extends Fragment {


    private ListView listViewEventos;
    private String nombreUsuario;
    private Integer idUsuario;
    private AdaptadorUsuarioEventos adapter;

    public FragmentDetalleUsuarioEvento() {
        // Required empty public constructor
    }
    public static FragmentDetalleUsuarioEvento newInstance(TransferUsuario usuario,
                                                    ArrayList<String> nombresEventos,
                                                    ArrayList<String> asistenciaEventos) {
        FragmentDetalleUsuarioEvento frgDetalleEvento = new FragmentDetalleUsuarioEvento();
        Bundle bundle = new Bundle();
        bundle.putInt("id", usuario.getId());
        if(nombresEventos.size() != 0)
            bundle.putString("nombreUsuario","Eventos de " + usuario.getNombre());
        else
            bundle.putString("nombreUsuario","El usuario " + usuario.getNombre()+ " no tiene eventos");

        bundle.putStringArrayList("listaEventos", nombresEventos);
        bundle.putStringArrayList("listaEventosAsistencia", asistenciaEventos);

        frgDetalleEvento.setArguments(bundle);
        return frgDetalleEvento;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle != null) {
            idUsuario = bundle.getInt("id");
            ArrayList<String> listaEventos = bundle.getStringArrayList("listaEventos");
            ArrayList<String> listaUsuariosAsistencia = bundle.getStringArrayList("listaEventosAsistencia");
            nombreUsuario = bundle.getString("nombreUsuario");
            adapter = new AdaptadorUsuarioEventos(getActivity());
            adapter.setEventos(listaEventos);
            adapter.setAsistencia(listaUsuariosAsistencia);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detalle_usuario_evento, container, false);
        listViewEventos = (ListView) rootView.findViewById(R.id.listViewEventos);
        TextView titulo = (TextView) rootView.findViewById(R.id.textViewNombreEventosUsuario);
        titulo.setText(nombreUsuario);
        View header = inflater.inflate(R.layout.header_usuario_eventos, listViewEventos, false);
        listViewEventos.addHeaderView(header, null, false);
        listViewEventos.setAdapter(adapter);
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
