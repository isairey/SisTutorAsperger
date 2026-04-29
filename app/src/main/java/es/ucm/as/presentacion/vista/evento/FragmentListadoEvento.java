package es.ucm.as.presentacion.vista.evento;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;


public class FragmentListadoEvento extends Fragment {

    private ListView listadoEvento;
    private AdaptadorEventos adaptadorEventos;
    private ArrayList<String> listaEventos;
    private ArrayList<String> fechasEventos;
    private ArrayList<Integer> idsEventos;

    public static FragmentListadoEvento newInstance(ArrayList<TransferEvento> eventos) {
        FragmentListadoEvento frgEventoLista = new FragmentListadoEvento();
        Bundle bundle = new Bundle();
        ArrayList<String> nombresEventos = new ArrayList<String>();
        ArrayList<String> fechaEventos= new ArrayList<String>();
        ArrayList<Integer> idsEventos= new ArrayList<Integer>();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
        for(int i=0; i < eventos.size(); i++){
            nombresEventos.add(eventos.get(i).getNombre());
            fechaEventos.add(formatFecha.format(eventos.get(i).getFecha())+ " a las "+ formatHora.format(eventos.get(i).getHoraEvento()) );
            idsEventos.add(eventos.get(i).getId());
        }

        bundle.putStringArrayList("listaEventos", nombresEventos);
        bundle.putStringArrayList("fechasEventos", fechaEventos);
        bundle.putIntegerArrayList("idsEventos", idsEventos);
        frgEventoLista.setArguments(bundle);

        return frgEventoLista;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            listaEventos = bundle.getStringArrayList("listaEventos");
            fechasEventos = bundle.getStringArrayList("fechasEventos");
            idsEventos = bundle.getIntegerArrayList("idsEventos");
            adaptadorEventos = new AdaptadorEventos(getActivity());
            adaptadorEventos.setEventos(listaEventos);
            adaptadorEventos.setFechasEventos(fechasEventos);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_vacio, menu);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listado_evento, container, false);
        listadoEvento = (ListView)rootView.findViewById(R.id.ListadoEventos);
        listadoEvento.setAdapter(adaptadorEventos);
        listadoEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
            TransferEvento consultar = new TransferEvento();
            consultar.setId(idsEventos.get(pos)); // Busco por id
            Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_EVENTO, consultar);
            }
        });

        return rootView;
    }


}
