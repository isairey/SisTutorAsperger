package es.ucm.as.presentacion.vista.usuario.tarea;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.negocio.utils.Frecuencia;
import es.ucm.as.negocio.utils.ParserTime;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;


public class UsuarioTareaDetalleActivity extends AppCompatActivity {

    private boolean nuevaTarea;
    private Integer idUsuario;
    private Integer idTarea;
    private Frecuencia frecuencia;
    private EditText textoAlarma;
    private EditText textoPregunta;
    private EditText mejorar;
    private TextView si;
    private TextView no;
    private TimePicker horaAlarma;
    private TimePicker horaPregunta;
    private RadioGroup frecuenciasRadio;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_usuario_tarea_detalle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Se buscan las views por su id
        this.textoAlarma = (EditText) findViewById(R.id.textoAlarma);
        this.textoPregunta = (EditText) findViewById(R.id.textoPregunta);
        this.si = (TextView) findViewById(R.id.si);
        this.no = (TextView) findViewById(R.id.no);
        this.mejorar = (EditText) findViewById(R.id.mejora);
        this.horaAlarma = (TimePicker) findViewById(R.id.horaAlarma);
        this.horaPregunta = (TimePicker) findViewById(R.id.horaPregunta);
        this.frecuenciasRadio = (RadioGroup) findViewById(R.id.radioFrecuencias);
        this.total = (TextView) findViewById(R.id.total);

        frecuenciasRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.diaria){
                    frecuencia = Frecuencia.DIARIA;
                    frecuenciasRadio.check(R.id.diaria);
                }else if (checkedId == R.id.semanal){
                    frecuencia = Frecuencia.SEMANAL;
                    frecuenciasRadio.check(R.id.semanal);
                }else if (checkedId == R.id.mensual){
                    frecuencia = Frecuencia.MENSUAL;
                    frecuenciasRadio.check(R.id.mensual);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        idUsuario = bundle.getInt("usuario");
        idTarea = bundle.getInt("idTarea");

        if (!bundle.get("nueva").equals("nueva")) { // Editar tarea
            nuevaTarea = false;
            textoAlarma.setEnabled(false);
            completarCampos(bundle);
        } else {                                     // Crear tarea
            nuevaTarea = true;
            seleccionaRadioButton("diaria");
        }
    }

    // Este metodo carga los valores de la BBDD en los campos
    private void completarCampos(Bundle bundle){
        textoAlarma.setText(bundle.getString("txtAlarma"));
        textoPregunta.setText(bundle.getString("txtPregunta"));
        si.setText(bundle.get("si").toString());
        no.setText(bundle.get("no").toString());
        mejorar.setText(bundle.get("mejorar").toString());
        total.setText(bundle.get("total").toString());
        // TimePicker hora alarma
        String horaLargo = bundle.getString("horaAlarma").trim();
        horaAlarma.setCurrentHour(ParserTime.hour(horaLargo));
        horaAlarma.setCurrentMinute(ParserTime.min(horaLargo));
        // TimePicker hora pregunta
        horaLargo = bundle.getString("horaPregunta").trim();
        horaPregunta.setCurrentHour(ParserTime.hour(horaLargo));
        horaPregunta.setCurrentMinute(ParserTime.min(horaLargo));
        // Frecuencia
        seleccionaRadioButton(bundle.getString("frecuencia"));
    }

    private Frecuencia seleccionaRadioButton(String string){
        switch (string){
            case "Diaria":
                frecuenciasRadio.clearCheck();
                frecuencia = Frecuencia.DIARIA;
                RadioButton diaria = (RadioButton) findViewById(R.id.diaria);
                diaria.setChecked(true);
                frecuenciasRadio.check(R.id.diaria);
                break;
            case "Semanal":
                frecuenciasRadio.clearCheck();
                frecuencia = Frecuencia.SEMANAL;
                RadioButton semanal = (RadioButton) findViewById(R.id.semanal);
                semanal.setChecked(true);
                frecuenciasRadio.check(R.id.semanal);
                break;
            case "Mensual":
                frecuenciasRadio.clearCheck();
                frecuencia = Frecuencia.MENSUAL;
                RadioButton mensual = (RadioButton) findViewById(R.id.mensual);
                mensual.setChecked(true);
                frecuenciasRadio.check(R.id.mensual);
                break;
        }
        return frecuencia;
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacio, menu);
        return true;
    }


    public void aceptar(View view){

        if(textoAlarma.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "El campo del texto de la alarma es obligatorio",
                    Toast.LENGTH_SHORT).show();
        }else {
            String alarm_hour = horaAlarma.getCurrentHour() + ":" + horaAlarma.getCurrentMinute();
            String pregunta_hour = horaPregunta.getCurrentHour() + ":" + horaPregunta.getCurrentMinute();

            TransferTarea transfer = new TransferTarea();
            transfer.setTextoAlarma(textoAlarma.getText().toString());
            transfer.setTextoPregunta(textoPregunta.getText().toString());
            transfer.setHoraAlarma(ParserTime.toDate(alarm_hour));
            transfer.setHoraPregunta(ParserTime.toDate(pregunta_hour));

            transfer.setFrecuenciaTarea(frecuencia);
            transfer.setMejorar(Integer.parseInt(mejorar.getText().toString()));
            transfer.setNumSi(Integer.parseInt(si.getText().toString()));
            transfer.setNumNo(Integer.parseInt(no.getText().toString()));
            transfer.setHabilitada(true);
            transfer.setId(idTarea);
            TransferUsuario t_usuario = new TransferUsuario();
            t_usuario.setId(idUsuario);
            transfer.setUsuario(t_usuario);

            if (nuevaTarea) {
                Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_TAREA, transfer);
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TAREAS, idUsuario);
            } else {
                Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_TAREA, transfer);
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TAREAS, idUsuario);
            }
        }
    }

    public void cancelar(View view){
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_TAREAS, idUsuario);
    }
}
