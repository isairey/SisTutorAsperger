package es.ucm.as.presentacion.vista.ayuda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.ucm.as.R;

public class FragmentListadoAyuda extends Fragment {

    private ListView listadoAyuda;

    public FragmentListadoAyuda() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listado_ayuda, container, false);

        listadoAyuda = (ListView)rootView.findViewById(R.id.listaAyuda);

        ArrayAdapter<CharSequence> adapterAyuda = ArrayAdapter.createFromResource(
                this.getActivity(), R.array.faq, android.R.layout.simple_list_item_1);
        listadoAyuda.setAdapter(adapterAyuda);

        // Se habilita la posibilidad de seleccionar una fila para que se muestre un menu
        listadoAyuda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos", pos);
                FragmentDetalleAyuda frgAyuda = new FragmentDetalleAyuda();
                frgAyuda.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrgDetalle, frgAyuda).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
