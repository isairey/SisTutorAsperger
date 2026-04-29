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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import es.ucm.as.R;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.main.Manager;

public class FragmentDetalleUsuario extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;

    private TextView nombreV;
    private TextView sincronizacionV;
    private TextView perfilV;
    private EditText dniV;
    private EditText direccionV;
    private EditText telefonoV;
    private EditText correoV;
    private EditText centroEstudiosV;
    private EditText estudiosV;
    private EditText notasV;
    private TextView puntuacionV;
    private ImageView avatarV;
    private TextView infoPadreV;
    private TextView infoMadreV;
    private FloatingActionButton editarDatos;

    private static Integer idUsuario;
    private static String nombre;
    private String sincronizacion;
    private String perfil;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;
    private String centroEstudios;
    private String estudios;
    private String notas;
    private Integer puntuacion;
    private String avatar;
    private String nombrePadre;
    private String nombreMadre;
    private String telefonoPadre;
    private String telefonoMadre;
    private String correoPadre;
    private String correoMadre;

    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PATRON_DNI = "^[0-9]{8}[A-Z]$";

    private static final String PATRON_TELEFONO = "^[0-9]{9}$";

    public FragmentDetalleUsuario(){}

    public static FragmentDetalleUsuario newInstance(TransferUsuario usuario) {
        FragmentDetalleUsuario frgUsuario = new FragmentDetalleUsuario();
        idUsuario = usuario.getId();
        Bundle arguments = new Bundle();
        arguments.putString("nombre", usuario.getNombre());
        arguments.putString("correo", usuario.getCorreo());
        arguments.putString("avatar", usuario.getAvatar());
        arguments.putString("telefono", usuario.getTelefono());
        arguments.putInt("puntuacion", usuario.getPuntuacion());
        arguments.putString("estudios", usuario.getCurso());
        arguments.putString("dni", usuario.getDni());
        arguments.putString("direccion", usuario.getDireccion());
        arguments.putString("perfil", usuario.getTipoPerfil());
        arguments.putString("notas", usuario.getNotas());
        arguments.putString("nombrePadre", usuario.getNombrePadre());
        arguments.putString("nombreMadre", usuario.getNombreMadre());
        arguments.putString("correoPadre", usuario.getCorreoPadre());
        arguments.putString("correoMadre", usuario.getCorreoMadre());
        arguments.putString("telfPadre", usuario.getTelPadre());
        arguments.putString("telfMadre", usuario.getTelMadre());
        arguments.putString("colegio", usuario.getCentroAcademico());
        arguments.putString("sincronizacion", usuario.getCodigoSincronizacion());

        frgUsuario.setArguments(arguments);

        return frgUsuario;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Bundle bundle = getArguments();
        if(bundle != null) {
            nombre = bundle.getString("nombre");
            correo = bundle.getString("correo");
            avatar = bundle.getString("avatar");
            telefono = bundle.getString("telefono");
            puntuacion = bundle.getInt("puntuacion");
            estudios = bundle.getString("estudios");
            dni = bundle.getString("dni");
            direccion = bundle.getString("direccion");
            perfil = bundle.getString("perfil");
            notas = bundle.getString("notas");
            nombrePadre = bundle.getString("nombrePadre");
            nombreMadre = bundle.getString("nombreMadre");
            telefonoPadre = bundle.getString("telfPadre");
            telefonoMadre = bundle.getString("telfMadre");
            correoPadre = bundle.getString("correoPadre");
            correoMadre = bundle.getString("correoMadre");
            centroEstudios = bundle.getString("colegio");
            sincronizacion = bundle.getString("sincronizacion");
        }
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detalle_usuario, container, false);

            nombreV = (TextView) rootView.findViewById(R.id.nombre);
            sincronizacionV = (TextView) rootView.findViewById(R.id.sincronizacion);
            perfilV = (TextView) rootView.findViewById(R.id.perfil);
            dniV = (EditText) rootView.findViewById(R.id.dni);
            direccionV = (EditText) rootView.findViewById(R.id.direccion);
            telefonoV = (EditText) rootView.findViewById(R.id.telefono);
            correoV = (EditText) rootView.findViewById(R.id.correo);
            centroEstudiosV = (EditText) rootView.findViewById(R.id.colegio);
            estudiosV = (EditText) rootView.findViewById(R.id.estudios);
            notasV = (EditText) rootView.findViewById(R.id.notas);
            puntuacionV = (TextView) rootView.findViewById(R.id.puntuacionUsuario);
            avatarV = (ImageView) rootView.findViewById(R.id.avatar);
            infoPadreV = (TextView) rootView.findViewById(R.id.infoPadre);
            infoMadreV = (TextView) rootView.findViewById(R.id.infoMadre);
            editarDatos = (FloatingActionButton) rootView.findViewById(R.id.guardarCambios);

            nombreV.setText(nombre);
            sincronizacionV.setText(sincronizacion);
            perfilV.setText("Perfil " + perfil);
            dniV.setText(dni);
            direccionV.setText(direccion);
            telefonoV.setText(telefono);
            correoV.setText(correo);
            centroEstudiosV.setText(centroEstudios);
            estudiosV.setText(estudios);
            notasV.setText(notas);
            puntuacionV.setText(puntuacion + "/10");
            if(avatar != null && !avatar.equals(""))
                avatarV.setImageBitmap(BitmapFactory.decodeFile(avatar));
            else
                avatarV.setImageResource(R.drawable.avatar);

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
                                getActivity().startActivityForResult(intent, CAMARA);
                            } else if (items[item].equals("Elegir de la galeria")) {
                                Intent intent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                getActivity().startActivityForResult(Intent.createChooser(intent, "Select File"), SELECCIONAR_GALERIA);
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

            editarDatos.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombre = nombreV.getText().toString();
                    correo = correoV.getText().toString();
                    telefono = telefonoV.getText().toString();
                    estudios = estudiosV.getText().toString();
                    dni = dniV.getText().toString();
                    direccion = direccionV.getText().toString();
                    notas = notasV.getText().toString();
                    centroEstudios = centroEstudiosV.getText().toString();
                    if(validarDatosUsuario()) {
                        TransferUsuario usuario = new TransferUsuario();
                        usuario.setNombre(nombre);
                        usuario.setCorreo(correo);
                        usuario.setAvatar(avatar);
                        usuario.setTelefono(telefono);
                        usuario.setCurso(estudios);
                        usuario.setDni(dni);
                        usuario.setDireccion(direccion);
                        usuario.setNotas(notas);
                        usuario.setCentroAcademico(centroEstudios);
                        usuario.setId(idUsuario);
                        Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_USUARIO, usuario);
                        Toast.makeText(getActivity(), "La informacion del usuario ha sido " +
                                "actualizada con éxito", Toast.LENGTH_SHORT).show();
                        Controlador.getInstancia().ejecutaComando(ListaComandos.LISTADO_USUARIOS, null);
                    }
                }
            });

            infoPadreV.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog a = createInfoProgenitoresDialogo("padre", inflater);
                    a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    a.show();
                }
            });

            infoMadreV.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog a = createInfoProgenitoresDialogo("madre", inflater);
                    a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    a.show();
                }
            });

            return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         //super.onActivityResult(requestCode, resultCode, data);
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
                CursorLoader cursorLoader = new CursorLoader(Manager.getInstance().getActivity(),
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
    private void mostrarMensajeError(String msg) {
        Toast errorNombre =
                Toast.makeText(Manager.getInstance().getContext().getApplicationContext(),
                        msg, Toast.LENGTH_SHORT);
        errorNombre.show();
    }

    private boolean datosObligatoriosValidos(){
        if (!nombre.matches(""))
            return true;
        else
            mostrarMensajeError("El campo nombre no puede ser vacío");

        return false;
    }

    private boolean algunCampoRelleno(){
        return !correo.matches("") || !telefono.matches("") || !estudios.matches("") || !dni.matches("")
                || !direccion.matches("") || !notas.matches("") || !nombrePadre.matches("")
                || !nombreMadre.matches("") || !correoPadre.matches("") || !correoMadre.matches("")
                || !telefonoPadre.matches("") || !telefonoMadre.matches("") || !centroEstudios.matches("");
    }

    private boolean validarDatosUsuario(){
        boolean buenos = datosObligatoriosValidos();

        if(buenos && algunCampoRelleno()){
            return datosValidos();
        }
        else
            return buenos;
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

        if(!telefono.matches("") && !telefono.matches(PATRON_TELEFONO)){
            correoValido = false;
            mostrarMensajeError("Campo telefono inválido");
        }


        return dniValido && correoValido && telefonoValido;
    }


    public AlertDialog createInfoProgenitoresDialogo(final String progenitor, LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = inflater.inflate(R.layout.dialog_info_padres, null);
        builder.setView(v);

        final EditText name = (EditText) v.findViewById(R.id.name);
        final EditText phone = (EditText) v.findViewById(R.id.phone);
        final EditText mail = (EditText) v.findViewById(R.id.mail);

        if (progenitor.equals("padre")) {
            builder.setTitle("Información del padre");
            name.setText(nombrePadre);
            phone.setText(telefonoPadre);
            mail.setText(correoPadre);
        }
        else{
            builder.setTitle("Información de la madre");
            name.setText(nombreMadre);
            phone.setText(telefonoMadre);
            mail.setText(correoMadre);
        }

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validarDatosPadres(mail.getText().toString(), phone.getText().toString())) {
                    TransferUsuario editUser = new TransferUsuario();
                    editUser.setId(idUsuario);
                    if (progenitor.equals("padre")) {
                        editUser.setNombrePadre(name.getText().toString());
                        editUser.setTelPadre(phone.getText().toString());
                        editUser.setCorreoPadre(mail.getText().toString());
                    }
                    else {
                        editUser.setNombreMadre(name.getText().toString());
                        editUser.setTelMadre(phone.getText().toString());
                        editUser.setCorreoMadre(mail.getText().toString());
                    }
                    Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_USUARIO, editUser);
                    Toast.makeText(getActivity(), "La informacion de los padres ha sido" +
                            " actualizada con éxito", Toast.LENGTH_SHORT).show();
                    Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, idUsuario);
                }
            }
        });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    private boolean validarDatosPadres(String correoProgenitor, String telefonoProgenitor){

        Boolean correoProgenitorValido = true;
        Boolean telefonoProgenitorValido = true;

        if(!correoProgenitor.matches("") && !correoProgenitor.matches(PATRON_EMAIL)){
            correoProgenitorValido = false;
            mostrarMensajeError("Campo correo inválido");
        }

        if(!telefonoProgenitor.matches("") && !telefonoProgenitor.matches(PATRON_TELEFONO)){
            telefonoProgenitorValido = false;
            mostrarMensajeError("Campo telefono inválido");
        }

        return correoProgenitorValido && telefonoProgenitorValido;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main_usuarios, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            case R.id.eliminarUsuario:
                DialogEliminarUsuario alertDialog = DialogEliminarUsuario.newInstance(idUsuario,nombre);
                alertDialog.show(getActivity().getFragmentManager(), "Eliminar usuario");
                break;
            case R.id.sincronizarUsuario:
                Controlador.getInstancia().ejecutaComando(ListaComandos.SINCRONIZAR, idUsuario); // Aqui iria el id usuario
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
