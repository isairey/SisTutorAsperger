package es.ucm.as.presentacion.vista.evento;

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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferUsuarioEvento;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.main.BlankFragment;
import es.ucm.as.presentacion.vista.main.Manager;


public class FragmentDetalleEvento extends Fragment{

    private static Integer idEventoActivo;
    private static Date fechaActualEvento;
    private ListView listaEventoUsuarios;
    private CalendarView calendarView;
    private AdaptadorEventoUsuarios adapterListadoUsuarios;
    private Button aceptar;
    private Button cancelar;
    private String nombreEvento;
    private String horaEvento;
    private String horaAlarma;
    private String fechaEvento;
    private View rootView;
    private String accion;
    private Date nuevaFechaEvento;
    private Calendar currentCalendar;
    private MaterialCalendarView customCalendar;

    public static FragmentDetalleEvento newInstance(TransferEvento evento,
                                                    ArrayList<String> nombresUsuarios,
                                                    ArrayList<Integer> idsUsuarios,
                                                    ArrayList<String> asistenciaUsuarios,
                                                    ArrayList<Integer> usuariosActivos,
                                                    String accion) {
        FragmentDetalleEvento frgEvento = new FragmentDetalleEvento();
        Bundle bundle = new Bundle();
        //datos
        bundle.putString("accion", accion);
        if(evento != null) {
            SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
            idEventoActivo = evento.getId();
            fechaActualEvento = evento.getFecha();
            bundle.putString("nombreEvento", evento.getNombre());
            bundle.putString("horaAlarma", formatHora.format(evento.getHoraAlarma()));
            bundle.putString("horaEvento", formatHora.format(evento.getHoraEvento()));
            bundle.putString("fechaEvento", formatFecha.format(evento.getFecha()));
        }
        else{
            bundle.putString("nombreEvento", "");
            bundle.putString("horaAlarma", "");
            bundle.putString("horaEvento", "");
        }
        bundle.putStringArrayList("listaUsuarios", nombresUsuarios);
        bundle.putIntegerArrayList("idsUsuarios", idsUsuarios);
        bundle.putIntegerArrayList("listaUsuariosActivos", usuariosActivos);
        bundle.putStringArrayList("listaUsuariosAsistencia", asistenciaUsuarios);

        frgEvento.setArguments(bundle);
        return frgEvento;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle != null) {
            ArrayList<String> listaUsuarios = bundle.getStringArrayList("listaUsuarios");
            ArrayList<String> listaUsuariosAsistencia = bundle.getStringArrayList("listaUsuariosAsistencia");
            ArrayList<Integer> listaUsuariosActivos = bundle.getIntegerArrayList("listaUsuariosActivos");
            ArrayList<Integer> idsUsers =bundle.getIntegerArrayList("idsUsuarios");
            nombreEvento = bundle.getString("nombreEvento");
            horaEvento = bundle.getString("horaEvento");
            horaAlarma = bundle.getString("horaAlarma");
            fechaEvento = bundle.getString("fechaEvento");
            accion = bundle.getString("accion");
            adapterListadoUsuarios = new AdaptadorEventoUsuarios(getActivity());
            adapterListadoUsuarios.setDatos(listaUsuarios);
            adapterListadoUsuarios.setDatosIds(idsUsers);
            adapterListadoUsuarios.setDatosAsistencia(listaUsuariosAsistencia);

            ArrayList<Boolean> usuariosActivos = new ArrayList<Boolean>();
            for(int i = 0; i < listaUsuariosActivos.size();i++){
                if(listaUsuariosActivos.get(i) == 1)
                    usuariosActivos.add(true);
                else
                    usuariosActivos.add(false);
            }

            adapterListadoUsuarios.setDatosCheck(usuariosActivos);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detalle_evento, container, false);
        listaEventoUsuarios = (ListView) rootView.findViewById(R.id.listViewUsuariosEvento);
        View header = inflater.inflate(R.layout.row_evento_listado_header, listaEventoUsuarios, false);
        aceptar= (Button) rootView.findViewById(R.id.botonAccionEvento);
        cancelar = (Button) rootView.findViewById(R.id.botonCancelarEvento);
        TextView textViewNombreEvento = (TextView) rootView.findViewById(R.id.DetalleNombreEvento);
        final EditText editTextNombreEvento = (EditText) rootView.findViewById(R.id.editTextNombreEvento);
        final EditText editTextHoraAlarma = (EditText) rootView.findViewById(R.id.editTextHoraAlarma);
        final EditText editTextHoraEvento = (EditText) rootView.findViewById(R.id.editTextHoraEvento);
        customCalendar = (MaterialCalendarView) rootView.findViewById(R.id.calendar_view);

        String dia;
        String mes;
        String anyo;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        if(accion.equals("pasado")){
            dia = fechaEvento.substring(0, 2);
            mes = fechaEvento.substring(3, 5);
            anyo = fechaEvento.substring(6);
            currentCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
            currentCalendar.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
            currentCalendar.set(Calendar.YEAR, Integer.parseInt(anyo));
            textViewNombreEvento.setText(nombreEvento);
            editTextNombreEvento.setText(nombreEvento);
            editTextHoraAlarma.setText(horaAlarma);
            editTextHoraEvento.setText(horaEvento);
            editTextNombreEvento.setEnabled(false);
            editTextHoraAlarma.setEnabled(false);
            editTextHoraEvento.setEnabled(false);
            listaEventoUsuarios.setAdapter(adapterListadoUsuarios);
            listaEventoUsuarios.addHeaderView(header, null, false);
            listaEventoUsuarios.setFocusableInTouchMode(false);
            listaEventoUsuarios.setClickable(false);
            customCalendar.setSelectedDate(currentCalendar.getTime());
            customCalendar.setCurrentDate(currentCalendar);
            customCalendar.setMinimumDate(currentCalendar);
            customCalendar.setMaximumDate(currentCalendar);
            aceptar.setVisibility(View.GONE);
            cancelar.setVisibility(View.GONE);
        }
        else {
            if (nombreEvento.equals(""))
                textViewNombreEvento.setText("Nuevo evento");
            else {
                dia = fechaEvento.substring(0, 2);
                mes = fechaEvento.substring(3, 5);
                anyo = fechaEvento.substring(6);

                currentCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
                currentCalendar.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
                currentCalendar.set(Calendar.YEAR, Integer.parseInt(anyo));
                textViewNombreEvento.setText(nombreEvento);
            }
            editTextNombreEvento.setText(nombreEvento);
            if (accion.equals("guardar")) {
                editTextNombreEvento.setEnabled(false);
            }
            editTextHoraAlarma.setText(horaAlarma);
            editTextHoraEvento.setText(horaEvento);

            listaEventoUsuarios.setAdapter(adapterListadoUsuarios);
            listaEventoUsuarios.addHeaderView(header, null, false);

            listaEventoUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapterListadoUsuarios.cambiaCheck(position - 1); // Si tienes header sino cambiaCheck(position);
                }
            });

            // Fin Listado de usuarios del evento*/
            customCalendar.setSelectedDate(currentCalendar.getTime());
            customCalendar.setCurrentDate(currentCalendar);
            customCalendar.setMinimumDate(Calendar.getInstance().getTime());
            fechaActualEvento = currentCalendar.getTime();
            customCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    fechaActualEvento = date.getCalendar().getTime();
                }
            });

            if (accion.equals("guardar")) {
                aceptar.setText("guardar");
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Guardar
                        if (!editTextNombreEvento.getText().toString().matches("") &&
                                !editTextHoraAlarma.getText().toString().matches("") &&
                                !editTextHoraEvento.getText().toString().matches("")) {

                            try {
                                TransferEvento nuevoEvento = new TransferEvento();
                                nuevoEvento.setId(idEventoActivo);
                                nuevoEvento.setNombre(editTextNombreEvento.getText().toString());
                                nuevoEvento.setFecha(fechaActualEvento);
                                SimpleDateFormat df_envio_datos = new SimpleDateFormat("HH:mm");
                                Date horaAlarmaEnvio = df_envio_datos.parse(editTextHoraAlarma.getText().toString());
                                Date horaEventoEnvio = df_envio_datos.parse(editTextHoraEvento.getText().toString());
                                if (horaEventoEnvio.after(horaAlarmaEnvio) && horaAlarmaEnvio.getTime() != horaEventoEnvio.getTime()) {
                                    nuevoEvento.setHoraAlarma(arregloDatesEvento(fechaActualEvento, horaAlarmaEnvio));
                                    nuevoEvento.setHoraEvento(arregloDatesEvento(fechaActualEvento, horaEventoEnvio));
                                    Controlador.getInstancia().ejecutaComando(ListaComandos.GUARDAR_EVENTO, nuevoEvento);
                                    //Añadir usuarios al evento
                                    List<TransferUsuarioEvento> listaUsuarios = new ArrayList<TransferUsuarioEvento>();
                                    TransferUsuarioEvento evento = new TransferUsuarioEvento(nuevoEvento, null);
                                    listaUsuarios.add(evento);
                                    for (int i = 0; i < adapterListadoUsuarios.getDatosCheck().size(); i++) {

                                        if (adapterListadoUsuarios.getDatosCheck().get(i) == true) { // Si está marcado
                                            TransferUsuario add = new TransferUsuario();
                                            add.setId(adapterListadoUsuarios.getDatosIds().get(i));
                                            add.setNombre(adapterListadoUsuarios.getItem(i));

                                            TransferUsuarioEvento eventoUsuario = new TransferUsuarioEvento(nuevoEvento, add);
                                            eventoUsuario.setActivo(1);
                                            eventoUsuario.setAsistencia(adapterListadoUsuarios.getAsistencia().get(i));
                                            listaUsuarios.add(eventoUsuario);
                                        }

                                    }

                                    Controlador.getInstancia().ejecutaComando(ListaComandos.GUARDAR_USUARIOS_EVENTO, listaUsuarios);
                                    Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_EVENTOS, null);
                                } else {
                                    Toast errorNombre =
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "¡Error! Hora de alarma posterior/igual a la hora del evento", Toast.LENGTH_SHORT);

                                    errorNombre.show();
                                }
                            } catch (ParseException e) {
                                Toast errorNombre =
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Error: Formato incorrecto de hora", Toast.LENGTH_SHORT);
                                errorNombre.show();
                            }


                        } else {
                            Toast errorNombre =
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Error: Campo/s vacíos", Toast.LENGTH_SHORT);
                            errorNombre.show();
                        }
                    }
                });
            } else {
                aceptar.setText("Crear");
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Crear
                        if (!editTextNombreEvento.getText().toString().matches("") &&
                                !editTextHoraAlarma.getText().toString().matches("") &&
                                !editTextHoraEvento.getText().toString().matches("")) {

                            try {
                                TransferEvento nuevoEvento = new TransferEvento();
                                nuevoEvento.setNombre(editTextNombreEvento.getText().toString());
                                nuevoEvento.setFecha(fechaActualEvento);
                                SimpleDateFormat df_envio_datos = new SimpleDateFormat("HH:mm");
                                Date horaAlarmaEnvio = df_envio_datos.parse(editTextHoraAlarma.getText().toString());
                                Date horaEventoEnvio = df_envio_datos.parse(editTextHoraEvento.getText().toString());
                                if (horaEventoEnvio.after(horaAlarmaEnvio) && horaAlarmaEnvio.getTime() != horaEventoEnvio.getTime()) {
                                    nuevoEvento.setHoraAlarma(arregloDatesEvento(fechaActualEvento, horaAlarmaEnvio));
                                    nuevoEvento.setHoraEvento(arregloDatesEvento(fechaActualEvento, horaEventoEnvio));
                                    Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_EVENTO, nuevoEvento);

                                    //Añadir usuarios al evento
                                    List<TransferUsuarioEvento> listaUsuarios = new ArrayList<TransferUsuarioEvento>();
                                    for (int i = 0; i < adapterListadoUsuarios.getDatosCheck().size(); i++) {

                                        if (adapterListadoUsuarios.getDatosCheck().get(i) == true) { // Si está marcado
                                            TransferUsuario add = new TransferUsuario();
                                            add.setId(adapterListadoUsuarios.getDatosIds().get(i));
                                            add.setNombre(adapterListadoUsuarios.getItem(i));

                                            TransferUsuarioEvento eventoUsuario = new TransferUsuarioEvento(nuevoEvento, add);
                                            eventoUsuario.setActivo(1);
                                            eventoUsuario.setAsistencia("NO");
                                            listaUsuarios.add(eventoUsuario);
                                        }

                                    }

                                    Controlador.getInstancia().ejecutaComando(ListaComandos.ANYADIR_USUARIOS_EVENTO, listaUsuarios);
                                    Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_EVENTOS, null);
                                } else {
                                    Toast errorNombre =
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "¡Error! Hora de alarma posterior/igual a la hora del evento", Toast.LENGTH_SHORT);

                                    errorNombre.show();
                                }
                            } catch (ParseException e) {
                                Toast errorNombre =
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Error: Formato incorrecto de hora", Toast.LENGTH_SHORT);

                                errorNombre.show();
                            }

                        } else {
                            Toast errorNombre =
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Error: Campo/s vacíos", Toast.LENGTH_SHORT);

                            errorNombre.show();
                        }
                    }
                });
            }
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlankFragment del = new BlankFragment();
                    Manager.getInstance().getFragmentManager().beginTransaction().replace(R.id.FrgDetalle, del).commit();
                }
            });
        }
        return rootView;

    }

    public Date arregloDatesEvento(Date evento, Date hora){

        SimpleDateFormat horasMinutos = new SimpleDateFormat("HH:mm");
        StringTokenizer tokens = new StringTokenizer(horasMinutos.format
                (hora),":");
        Integer h = Integer.parseInt(tokens.nextToken());
        Integer m =  Integer.parseInt(tokens.nextToken());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(evento);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        if(accion.equals("guardar") || accion.equals("pasado"))
          inflater.inflate(R.menu.menu_main_eventos, menu);
        else//Crear
          inflater.inflate(R.menu.menu_vacio, menu);

}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.eliminarEvento:
                DialogEliminarEvento alertDialog = DialogEliminarEvento.newInstance(idEventoActivo,nombreEvento);
                alertDialog.show(getActivity().getFragmentManager(), "Eliminar evento");
                break;
        }
        return true;
    }

}
