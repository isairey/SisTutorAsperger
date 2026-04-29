package es.ucm.as.presentacion.vista.usuario;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import es.ucm.as.R;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.negocio.utils.Perfil;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.main.BlankFragment;
import es.ucm.as.presentacion.vista.main.Manager;

public class FragmentDetalleNuevoUsuario extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;

    private EditText nombreV;
    private Spinner perfilV;
    private EditText dniV;
    private EditText direccionV;
    private EditText telefonoV;
    private EditText correoV;
    private EditText centroEstudiosV;
    private EditText estudiosV;
    private EditText notasV;
    private ImageView avatarV;
    private TextView nombrePadreV;
    private TextView nombreMadreV;
    private TextView telPadreV;
    private TextView telMadreV;
    private TextView correoPadreV;
    private TextView correoMadreV;
    private Button aceptar;
    private Button cancelar;
    private Spinner spinnerPerfiles;

    private String nombre;
    private String perfil;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;
    private String centroEstudios;
    private String estudios;
    private String notas;
    private String avatar;
    private String nombrePadre;
    private String nombreMadre;
    private String telefonoPadre;
    private String telefonoMadre;
    private String correoPadre;
    private String correoMadre;
    private String[] nombresPerfiles={ Perfil.Estandar.toString(), Perfil.Chica.toString(), Perfil.Vacío.toString()};

    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PATRON_DNI = "^[0-9]{8}[A-Z]$";

    private static final String PATRON_TELEFONO = "^[0-9]{9}$";

    public FragmentDetalleNuevoUsuario(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_vacio, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detalle_nuevo_usuario, container, false);

            nombreV = (EditText) rootView.findViewById(R.id.nuevoNombre);
            perfilV = (Spinner) rootView.findViewById(R.id.nuevoPerfil);
            dniV = (EditText) rootView.findViewById(R.id.nuevoDni);
            direccionV = (EditText) rootView.findViewById(R.id.nuevaDireccion);
            telefonoV = (EditText) rootView.findViewById(R.id.nuevoTelefono);
            correoV = (EditText) rootView.findViewById(R.id.nuevoCorreo);
            centroEstudiosV = (EditText) rootView.findViewById(R.id.nuevoColegio);
            estudiosV = (EditText) rootView.findViewById(R.id.nuevoEstudios);
            notasV = (EditText) rootView.findViewById(R.id.nuevoNotas);
            avatarV = (ImageView) rootView.findViewById(R.id.nuevaImagenUsuario);
            nombrePadreV = (TextView) rootView.findViewById(R.id.nuevoNombrePadre);
            nombreMadreV = (TextView) rootView.findViewById(R.id.nuevoNombreMadre);
            telPadreV = (TextView) rootView.findViewById(R.id.nuevoTelefonoPadre);
            telMadreV = (TextView) rootView.findViewById(R.id.nuevoTelefonoMadre);
            correoPadreV = (TextView) rootView.findViewById(R.id.nuevoCorreoPadre);
            correoMadreV = (TextView) rootView.findViewById(R.id.nuevoCorreoMadre);
            aceptar = (Button) rootView.findViewById(R.id.botonAceptarNuevoU);
            cancelar = (Button) rootView.findViewById(R.id.botonCancelarNuevoU);
            spinnerPerfiles = (Spinner) rootView.findViewById(R.id.nuevoPerfil);

            ArrayAdapter<String> adapter_perfiles= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, nombresPerfiles);
            adapter_perfiles
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPerfiles.setAdapter(adapter_perfiles);
            spinnerPerfiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerPerfiles.setSelection(position);
                    perfil = (String) spinnerPerfiles.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            avatar = "";

            avatarV.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] items = { "Hacer foto", "Elegir de la galeria", "Imagen por defecto" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Hacer foto")) {
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(intent, CAMARA);
                            } else if (items[item].equals("Elegir de la galeria")) {
                                Intent intent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(
                                        Intent.createChooser(intent, "Select File"),
                                        SELECCIONAR_GALERIA);
                            } else if (items[item].equals("Imagen por defecto")) {
                                avatarV.setImageResource(R.drawable.avatar);
                                avatar="";
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.create().show();
                }
            });

            aceptar.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombre = nombreV.getText().toString();
                    correo = correoV.getText().toString();
                    telefono = telefonoV.getText().toString();
                    estudios = estudiosV.getText().toString();
                    dni = dniV.getText().toString();
                    direccion = direccionV.getText().toString();
                    notas = notasV.getText().toString();
                    nombrePadre = nombrePadreV.getText().toString();
                    nombreMadre = nombreMadreV.getText().toString();
                    correoPadre = correoPadreV.getText().toString();
                    correoMadre = correoMadreV.getText().toString();
                    telefonoPadre = telPadreV.getText().toString();
                    telefonoMadre = telMadreV.getText().toString();
                    centroEstudios = centroEstudiosV.getText().toString();

                    if (validar()) {
                        TransferUsuario usuario = new TransferUsuario();
                        usuario.setNombre(nombre);
                        usuario.setCorreo(correo);
                        usuario.setAvatar(avatar);
                        usuario.setTelefono(telefono);
                        usuario.setPuntuacion(10);
                        usuario.setPuntuacionAnterior(10);
                        usuario.setCurso(estudios);
                        usuario.setDni(dni);
                        usuario.setDireccion(direccion);
                        usuario.setTipoPerfil(perfil);
                        usuario.setNotas(notas);
                        usuario.setNombrePadre(nombrePadre);
                        usuario.setNombreMadre(nombreMadre);
                        usuario.setCorreoPadre(correoPadre);
                        usuario.setCorreoMadre(correoMadre);
                        usuario.setTelPadre(telefonoPadre);
                        usuario.setTelMadre(telefonoMadre);
                        usuario.setCentroAcademico(centroEstudios);
                        long time = new Date().getTime();
                        String tmpStr = String.valueOf(time);
                        String last4Str = tmpStr.substring(tmpStr.length() - 5);
                        usuario.setCodigoSincronizacion(last4Str);

                        Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, usuario);
                        Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_USUARIOS, null);
                        BlankFragment fragmentBlank = new BlankFragment();
                        Manager.getInstance().getFragmentManager().beginTransaction()
                                .replace(R.id.FrgDetalle, fragmentBlank).commit();
                        Toast.makeText(getActivity(), "El usuario " + nombre
                                + " ha sido creado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            cancelar.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlankFragment fragmentBlank = new BlankFragment();
                    Manager.getInstance().getFragmentManager().beginTransaction()
                            .replace(R.id.FrgDetalle, fragmentBlank).commit();
                }
            });

        return rootView;
    }

    private boolean validar(){
        boolean buenos = datosObligatoriosValidos();

        if(buenos && algunCampoRelleno()){
            return datosValidos();
        }
        else
            return buenos;
    }

    private boolean algunCampoRelleno(){
        return !correo.matches("") || !telefono.matches("") || !estudios.matches("") || !dni.matches("")
                || !direccion.matches("") || !notas.matches("") || !nombrePadre.matches("")
                || !nombreMadre.matches("") || !correoPadre.matches("") || !correoMadre.matches("")
                || !telefonoPadre.matches("") || !telefonoMadre.matches("") || !centroEstudios.matches("");
    }

    private boolean datosObligatoriosValidos(){
        if (!nombre.matches("") && !perfil.matches(""))
            return true;
        else
            mostrarMensajeError("El campo del nombre del usuario no puede estar vacío");

        return false;
    }

    private boolean datosValidos() {

        Boolean dniValido = true;
        Boolean correoValido = true;
        Boolean telefonoValido = true;

        if(!dni.matches("") && !dni.matches(PATRON_DNI)){
            dniValido = false;
            mostrarMensajeError("Campo dni inválido");
        }

        if(!correo.matches("") && !correo.matches(PATRON_EMAIL)){
            correoValido = false;
            mostrarMensajeError("Campo correo inválido");
        }

        if(!correoPadre.matches("") && !correoPadre.matches(PATRON_EMAIL)){
            correoValido = false;
            mostrarMensajeError("Campo correo del padre inválido");
        }
        if(!correoMadre.matches("") && !correoMadre.matches(PATRON_EMAIL)){
            correoValido = false;
            mostrarMensajeError("Campo correo de la madre inválido");
        }

        if(!telefono.matches("") && !telefono.matches(PATRON_TELEFONO)){
            correoValido = false;
            mostrarMensajeError("Campo telefono inválido");
        }

        if(!telefonoPadre.matches("") && !telefonoPadre.matches(PATRON_TELEFONO)){
            correoValido = false;
            mostrarMensajeError("Campo telefono del padre inválido");
        }
        if(!telefonoMadre.matches("") && !telefonoMadre.matches(PATRON_TELEFONO)){
            correoValido = false;
            mostrarMensajeError("Campo telefono de la madre inválido");
        }
        return dniValido && correoValido && telefonoValido;
    }

    private void mostrarMensajeError(String msg) {
        Toast errorNombre =
                Toast.makeText(Manager.getInstance().getContext().getApplicationContext(),
                        msg, Toast.LENGTH_SHORT);
        errorNombre.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == CAMARA) {
                Bitmap imagen = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                avatarV.setImageBitmap(imagen);
                avatar = destination.getPath();
            } else if (requestCode == SELECCIONAR_GALERIA) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(Manager.getInstance().getContext(),
                        selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                avatarV.setImageBitmap(bm);
                avatar=selectedImagePath;
            }
        }
    }
}
