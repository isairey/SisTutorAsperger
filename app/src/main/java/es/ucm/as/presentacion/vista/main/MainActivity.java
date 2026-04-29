package es.ucm.as.presentacion.vista.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.ayuda.FragmentListadoAyuda;
import es.ucm.as.presentacion.vista.tutor.FragmentListadoTutor;


public class MainActivity extends AppCompatActivity {

    private String[] titulos;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private ArrayList<Item_Navegacion> NavItms;
    private TypedArray NavIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private AdaptadorNavegacion NavAdapter;
    private Menu menuActionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Manager.getInstance().setActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo);


        NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavList = (ListView) findViewById(R.id.lista);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        NavList.addHeaderView(header);
        NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
        titulos = getResources().getStringArray(R.array.nav_options);
        NavItms = new ArrayList<Item_Navegacion>();

        //Usuarios
        NavItms.add(new Item_Navegacion(titulos[0], NavIcons.getResourceId(0, -1)));
        //Eventos
        NavItms.add(new Item_Navegacion(titulos[1], NavIcons.getResourceId(1, -1)));
        //Mi perfil
        NavItms.add(new Item_Navegacion(titulos[2], NavIcons.getResourceId(2, -1)));
        //Ayuda
        NavItms.add(new Item_Navegacion(titulos[3], NavIcons.getResourceId(3, -1)));
        NavAdapter = new AdaptadorNavegacion(this, NavItms);
        NavList.setAdapter(NavAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                NavDrawerLayout,
                R.string.app_name,
                R.string.hello_world
        ) {

            public void onDrawerClosed(View view) {
                //Log.e("Cerrando drawer", "!!");
            }
            public void onDrawerOpened(View drawerView) {
                //Log.e("Abriendo drawe", "!!");
            }
        };

        // Establecemos que mDrawerToggle declarado anteriormente sea el DrawerListener
        NavDrawerLayout.setDrawerListener(mDrawerToggle);
        //Establecemos que el ActionBar muestre el Boton Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Establecemos la accion al clickear sobre cualquier item del menu.
        //De la misma forma que hariamos en una app comun con un listview.
        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                MostrarFragment(position);
            }
        });

        //Inicialemente se carga el listado de usuarios y el fragment grande en blanco
        // Luego carga los usuarios de la base de datos
        BlankFragment fragmentBlank = new BlankFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrgDetalle, fragmentBlank).commit();

        /*if(getIntent().getExtras() != null) {
            TransferUsuario transferUsuario = (TransferUsuario)getIntent().getExtras().getSerializable("usuario");
            Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO,transferUsuario.getId());
        }*/
        Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_USUARIOS,null);
    }

    private void MostrarFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 1: // Usuarios
                //Fragmento en blanco
                BlankFragment fragmentBlank = new BlankFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.FrgDetalle, fragmentBlank).commit();
                //Listado
                Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_USUARIOS, null);
                NavList.setItemChecked(position, true);
                NavList.setSelection(position);
                //Cambiamos el titulo en donde decia "
                setTitle(titulos[position - 1]);
                //Cerramos el menu deslizable
                NavDrawerLayout.closeDrawer(NavList);
                break;
            case 2: // Eventos
                //Fragmento en blanco
                BlankFragment fragmentDetalleTarea = new BlankFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.FrgDetalle, fragmentDetalleTarea).commit();
                Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_EVENTOS,null);

                NavList.setItemChecked(position, true);
                NavList.setSelection(position);
                //Cambiamos el titulo en donde decia "
                setTitle(titulos[position - 1]);
                //Cerramos el menu deslizable
                NavDrawerLayout.closeDrawer(NavList);
                break;
            case 3: // Mi perfil
                menuActionBar.clear();
                FragmentListadoTutor frgListadoTutor = new FragmentListadoTutor();
                Manager.getInstance().getFragmentManager().beginTransaction().replace(R.id.FrgListado, frgListadoTutor).commit();

                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TUTOR, null);
                NavList.setItemChecked(position, true);
                NavList.setSelection(position);
                setTitle(titulos[position - 1]);
                NavDrawerLayout.closeDrawer(NavList);
                break;
            case 4: // Ayuda
                menuActionBar.clear();
                BlankFragment fragmentoBlanco = new BlankFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.FrgDetalle, fragmentoBlanco).commit();

                FragmentListadoAyuda fragmentListadoAyuda = new FragmentListadoAyuda();
                getSupportFragmentManager().beginTransaction().replace(R.id.FrgListado, fragmentListadoAyuda).commit();

                NavList.setItemChecked(position, true);
                NavList.setSelection(position);
                setTitle(titulos[position - 1]);
                NavDrawerLayout.closeDrawer(NavList);
                break;

            default:
                //si no esta la opcion mostrara un toast y nos mandara a Home
                Toast.makeText(getApplicationContext(), "Opcion " + titulos[position - 1] + "no disponible!", Toast.LENGTH_SHORT).show();
                position = 1;
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // Actualizamos el contenido segun la opcion elegida
            NavList.setItemChecked(position, true);
            NavList.setSelection(position);
            //Cambiamos el titulo en donde decia "
            setTitle(titulos[position - 1]);
            //Cerramos el menu deslizable
            NavDrawerLayout.closeDrawer(NavList);
        } else {
            //Si el fragment es nulo mostramos un mensaje de error.
            //Log.e("Error  ", "MostrarFragment" + position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuActionBar = menu;
        getMenuInflater().inflate(R.menu.menu_vacio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) return true;
        else return false;
    }

    public void nuevoEvento(View view){
        menuActionBar.clear();
        getMenuInflater().inflate(R.menu.menu_vacio, menuActionBar);
        Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_EVENTO_CONSULTAR_USUARIOS,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (android.support.v4.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}