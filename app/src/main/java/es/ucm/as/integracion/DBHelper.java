package es.ucm.as.integracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

//Clase encargada de comunicarse con la base de datos, usa la librería OrmLite
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "as_tutor.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Tutor, Integer> tutorDao;
    private Dao<Evento, Integer> eventoDao;
    private Dao<Reto, Integer> retoDao;
    private Dao<Tarea, Integer> tareaDao;
    private Dao<Usuario, Integer> usuarioDao;
    private Dao<UsuarioEvento, Integer> usuarioEventoDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Tutor.class);
            TableUtils.createTable(connectionSource, Evento.class);
            TableUtils.createTable(connectionSource, Reto.class);
            TableUtils.createTable(connectionSource, Tarea.class);
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, UsuarioEvento.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<Tutor, Integer> getTutorDao() throws SQLException {
        if (tutorDao == null) {
            tutorDao = getDao(Tutor.class);
        }
        return tutorDao;
    }

    public Dao<Evento, Integer> getEventoDao() throws SQLException {
        if (eventoDao == null) {
            eventoDao = getDao(Evento.class);
        }
        return eventoDao;
    }
    public Dao<Reto, Integer> getRetoDao() throws SQLException {
        if (retoDao == null) {
            retoDao = getDao(Reto.class);
        }
        return retoDao;
    }
    public Dao<Tarea, Integer> getTareaDao() throws SQLException {
        if (tareaDao == null) {
            tareaDao = getDao(Tarea.class);
        }
        return tareaDao;
    }
    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<UsuarioEvento, Integer> getUsuarioEventoDao() throws SQLException {
        if (usuarioEventoDao == null) {
            usuarioEventoDao = getDao(UsuarioEvento.class);
        }
        return usuarioEventoDao;
    }

    @Override
    public void close() {
        super.close();
        tutorDao = null;
        eventoDao = null;
        retoDao = null;
        tareaDao = null;
        usuarioDao = null;
        usuarioEventoDao = null;
    }
    private PreparedQuery<Evento> EventosForUsuarioQuery = null;
    private PreparedQuery<Usuario> UsuariosForEventoQuery = null;

    public List<Evento> lookupEventosForUsuario(Usuario Usuario) throws SQLException {
        if (EventosForUsuarioQuery == null) {
            EventosForUsuarioQuery = makeEventosForUsuarioQuery();
        }
        EventosForUsuarioQuery.setArgumentHolderValue(0, Usuario);
        return getEventoDao().query(EventosForUsuarioQuery);
    }

    public List<Usuario> lookupUsuariosForEvento(Evento Evento) throws SQLException {
        if (UsuariosForEventoQuery == null) {
            UsuariosForEventoQuery = makeUsuariosForEventoQuery();
        }
        UsuariosForEventoQuery.setArgumentHolderValue(0, Evento);
        return getUsuarioDao().query(UsuariosForEventoQuery);
    }

    private PreparedQuery<Evento> makeEventosForUsuarioQuery() throws SQLException {

        QueryBuilder<UsuarioEvento, Integer> UsuarioEventoQb = getUsuarioEventoDao().queryBuilder();

        UsuarioEventoQb.selectColumns("EVENTO");
        SelectArg UsuarioSelectArg = new SelectArg();
        UsuarioEventoQb.where().eq("USUARIO", UsuarioSelectArg);

        QueryBuilder<Evento, Integer> EventoQb = getEventoDao().queryBuilder();

        EventoQb.where().in("ID", UsuarioEventoQb);
        return EventoQb.prepare();
    }

    private PreparedQuery<Usuario> makeUsuariosForEventoQuery() throws SQLException {
        QueryBuilder<UsuarioEvento, Integer> UsuarioEventoQb = getUsuarioEventoDao().queryBuilder();

        UsuarioEventoQb.selectColumns("USUARIO");
        SelectArg EventoSelectArg = new SelectArg();
        UsuarioEventoQb.where().eq("EVENTO", EventoSelectArg);

        QueryBuilder<Usuario, Integer> UsuarioQb = getUsuarioDao().queryBuilder();

        UsuarioQb.where().in("ID", UsuarioEventoQb);
        return UsuarioQb.prepare();
    }
}
