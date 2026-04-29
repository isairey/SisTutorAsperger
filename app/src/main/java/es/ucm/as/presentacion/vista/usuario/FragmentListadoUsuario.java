package es.ucm.as.presentacion.vista.usuario;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.main.Manager;


public class FragmentListadoUsuario extends Fragment {

	private ListView listadoUsuarios;
	private AdaptadorUsuarios adaptadorUsuarios;
	private FloatingActionButton nuevoUser;

	private ArrayList<Integer> ids;
	private ArrayList<String> nombres;
	private ArrayList<String> avatares;

	private Menu menuActionBar;


	public FragmentListadoUsuario() {
		// Required empty public constructor
	}

	public static FragmentListadoUsuario newInstance(ArrayList<TransferUsuario> usuarios) {
		FragmentListadoUsuario frgUsuarioLista = new FragmentListadoUsuario();

		ArrayList<Integer> ids = new ArrayList<Integer>();
		ArrayList<String> nombres = new ArrayList<String>();
		ArrayList<String> avatares = new ArrayList<String>();

		for(TransferUsuario transfer : usuarios){
			ids.add(transfer.getId());
			nombres.add(transfer.getNombre());
			avatares.add(transfer.getAvatar());
		}

		Bundle arguments = new Bundle();
		arguments.putIntegerArrayList("ids", ids);
		arguments.putStringArrayList("nombres", nombres);
		arguments.putStringArrayList("avatares", avatares);

		frgUsuarioLista.setArguments(arguments);

		return frgUsuarioLista;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (bundle != null) {
			ids = bundle.getIntegerArrayList("ids");
			nombres = bundle.getStringArrayList("nombres");
			avatares = bundle.getStringArrayList("avatares");
			adaptadorUsuarios = new AdaptadorUsuarios(getActivity());
			adaptadorUsuarios.setNombres(nombres);
			adaptadorUsuarios.setAvatares(avatares);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_listado_usuario, container, false);
		listadoUsuarios = (ListView)rootView.findViewById(R.id.listadoUsuarios);
		listadoUsuarios.setAdapter(adaptadorUsuarios);
		nuevoUser = (FloatingActionButton)rootView.findViewById(R.id.botonNuevoUsuario);

		nuevoUser.setOnClickListener(new AdapterView.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentDetalleNuevoUsuario fragmentNuevoUsuario = new FragmentDetalleNuevoUsuario();
				Manager.getInstance().getFragmentManager().beginTransaction()
						.replace(R.id.FrgDetalle, fragmentNuevoUsuario).commit();
			}
		});

		listadoUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
				Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, ids.get(pos));
			}
		});

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menuActionBar = menu;
		getActivity().getMenuInflater().inflate(R.menu.menu_vacio, menu);
	}
}

