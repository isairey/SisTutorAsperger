package es.ucm.as.presentacion.vista.usuario.tarea;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.main.Manager;

public class UsuarioTareasActivity extends AppCompatActivity {

    private Integer idUsuario;
    private Integer puntuacionActual;
    private Integer puntuacionAntes;
    private String nombreUsuario;
    private ArrayList<String> textosAlarma;
    private ArrayList<String> horasAlarma;
    private ArrayList<String> textosPreguntas;
    private ArrayList<String> horasPregunta;
    private ArrayList<Integer> si;
    private ArrayList<Integer> no;
    private ArrayList<String> frecuencias;
    private ArrayList<Integer> mejorar;
    private ArrayList<Integer> habilitada;
    private ArrayList<Integer> idsTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Manager.getInstance().setActivity(this);
        setContentView(R.layout.activity_usuario_tareas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo);

        textosAlarma = new ArrayList<String>();
        horasAlarma = new ArrayList<String>();
        textosPreguntas = new ArrayList<String>();
        horasPregunta = new ArrayList<String>();
        si = new ArrayList<Integer>();
        no = new ArrayList<Integer>();
        frecuencias = new ArrayList<String>();
        mejorar = new ArrayList<Integer>();
        habilitada = new ArrayList<Integer>();

        // Coge de la BBDD
        this.idUsuario = getIntent().getExtras().getInt("idUsuario");
        this.textosAlarma = getIntent().getStringArrayListExtra("textosAlarma");
        this.textosPreguntas = getIntent().getStringArrayListExtra("textosPregunta");
        this.si = getIntent().getIntegerArrayListExtra("si");
        this.no = getIntent().getIntegerArrayListExtra("no");
        this.frecuencias = getIntent().getStringArrayListExtra("frecuencias");
        this.habilitada = getIntent().getIntegerArrayListExtra("habilitada");
        this.mejorar = getIntent().getIntegerArrayListExtra("mejorar");
        this.horasAlarma = getIntent().getStringArrayListExtra("horaAlarmas");
        this.horasPregunta = getIntent().getStringArrayListExtra("horaPreguntas");
        this.idsTareas = getIntent().getIntegerArrayListExtra("idsTareas");

        this.nombreUsuario = getIntent().getExtras().getString("nombreUsuario");
        this.puntuacionActual = getIntent().getExtras().getInt("puntuacionActual");
        this.puntuacionAntes = getIntent().getExtras().getInt("puntuacionAntes");

        //Titulo
        final TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setText("Tareas de "+ nombreUsuario);
        // Puntuacion
        final TextView antes = (TextView) findViewById(R.id.puntuacionAnterior);
        final TextView actual = (TextView) findViewById(R.id.puntuacionAhora);
        final ImageView flecha = (ImageView) findViewById(R.id.flecha);

        antes.setText(puntuacionAntes.toString());
        actual.setText(puntuacionActual.toString());
        if(puntuacionActual - puntuacionAntes < 0)
            flecha.setImageResource(R.drawable.flecharoja);
        else
            flecha.setImageResource(R.drawable.flechaverde);

        // Creacion de la lista de tareas
        final ListView lv = (ListView) findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        // Se establece la fila cabecera
        View header = inflater.inflate(R.layout.tareas_header, lv, false);
        lv.addHeaderView(header, null, false);
        // Se rellenan las filas
        if(textosAlarma != null){
            AdaptadorTareas adapter = new AdaptadorTareas(textosAlarma, horasAlarma, textosPreguntas,
                    horasPregunta, si, no, frecuencias, habilitada, this.getApplicationContext());
            lv.setAdapter(adapter);
        }
        // Se habilita la posibilidad de seleccionar una fila para que se muestre un menu
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarMenuTarea(view, position);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacio, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO_ACTIVITY, idUsuario);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Muestra el menú con las opciones para la tarea seleccionada
    public void mostrarMenuTarea(View v, int position) {
        final int pos = position;
        if (position > 0) { // así no ocurre nada si se selecciona la cabecera
            position--;
            final CharSequence[] items = {"Editar", "Habilitar/deshabilitar", "Eliminar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioTareasActivity.this);
            builder.setTitle(textosAlarma.get(position));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Editar")) {
                        Intent intent = new Intent(getApplicationContext(), UsuarioTareaDetalleActivity.class);
                        intent.putExtra("nueva", "no"); // asi la UsuarioTareaDetalleActivity sabe que no es una tarea nueva
                        intent.putExtra("txtAlarma", textosAlarma.get(pos-1));
                        intent.putExtra("horaAlarma", horasAlarma.get(pos-1));
                        intent.putExtra("txtPregunta", textosPreguntas.get(pos-1));
                        intent.putExtra("horaPregunta", horasPregunta.get(pos-1));
                        intent.putExtra("frecuencia", frecuencias.get(pos-1));
                        intent.putExtra("si", si.get(pos-1));
                        intent.putExtra("no", no.get(pos - 1));
                        Integer total = si.get(pos-1) - no.get(pos-1);
                        intent.putExtra("total", total);
                        intent.putExtra("mejorar", mejorar.get(pos-1));
                        intent.putExtra("habilitada", habilitada.get(pos-1));
                        intent.putExtra("usuario", idUsuario);
                        intent.putExtra("idTarea", idsTareas.get(pos-1));
                        intent.setFlags(intent.getFlags() |Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    } else if (items[item].equals("Habilitar/deshabilitar")) {
                        Controlador.getInstancia().ejecutaComando(ListaComandos.DESHABILITAR_TAREA, idsTareas.get(pos-1));
                        Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TAREAS, idUsuario);
                    } else if(items[item].equals("Eliminar")) {
                        DialogEliminarTarea alertDialog = DialogEliminarTarea.newInstance(textosAlarma.get(pos-1),idsTareas.get(pos-1), idUsuario);
                        alertDialog.show(getSupportFragmentManager(), "Eliminar tarea");
                    }
                }
            });
            builder.show();
        }
    }
    // Metodo on click del boton "+" material design
    public void nuevaTarea(View view){
        Intent nuevaTarea = new Intent(this, UsuarioTareaDetalleActivity.class);
        nuevaTarea.putExtra("nueva", "nueva");
        nuevaTarea.putExtra("usuario", idUsuario);
        nuevaTarea.setFlags(nuevaTarea.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(nuevaTarea);
    }
}
