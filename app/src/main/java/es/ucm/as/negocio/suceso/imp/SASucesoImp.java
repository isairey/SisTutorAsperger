package es.ucm.as.negocio.suceso.imp;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as.integracion.DBHelper;
import es.ucm.as.integracion.UsuarioEvento;
import es.ucm.as.integracion.Evento;
import es.ucm.as.integracion.Reto;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.integracion.Tarea;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.suceso.TransferUsuarioEvento;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.integracion.Usuario;
import es.ucm.as.presentacion.vista.main.Manager;


public class SASucesoImp implements SASuceso {

    private DBHelper mDBHelper = null;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Manager.getInstance().getContext(), DBHelper.class);
        }
        return mDBHelper;
    }


	// Tarea
	@Override
	public void crearTarea(TransferTarea transferTarea) {
            // El constructor vacio de Tarea da valores por defecto a los campos no editables
            Tarea tarea = new Tarea();
            tarea.setTextoPregunta(transferTarea.getTextoPregunta());
            tarea.setTextoAlarma(transferTarea.getTextoAlarma());
            tarea.setHoraPregunta(transferTarea.getHoraPregunta());
            tarea.setHoraAlarma(transferTarea.getHoraAlarma());
            tarea.setMejorar(transferTarea.getMejorar());

            if (transferTarea.getFrecuenciaTarea() != null)
                tarea.setFrecuenciaTarea(transferTarea.getFrecuenciaTarea());
            if (transferTarea.getHabilitada() != null)
                tarea.setHabilitada(transferTarea.getHabilitada());

        try {
            Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();
            QueryBuilder<Usuario, Integer> uQb = daoUsuario.queryBuilder();
            uQb.where().idEq(transferTarea.getUsuario().getId());
            Usuario usuario = uQb.queryForFirst();
            tarea.setUsuario(usuario);

            Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();
            daoTarea.createOrUpdate(tarea);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    @Override
    public void editarTarea(TransferTarea transferTarea) {
        Tarea tarea = new Tarea();
        tarea.setTextoPregunta(transferTarea.getTextoPregunta());
        tarea.setTextoAlarma(transferTarea.getTextoAlarma());
        tarea.setHoraPregunta(transferTarea.getHoraPregunta());
        tarea.setHoraAlarma(transferTarea.getHoraAlarma());
        tarea.setMejorar(transferTarea.getMejorar());
        tarea.setFrecuenciaTarea(transferTarea.getFrecuenciaTarea());
        tarea.setHabilitada(transferTarea.getHabilitada());
        tarea.setNumSi(transferTarea.getNumSi());
        tarea.setNumNo(transferTarea.getNumNo());
        tarea.setContador(transferTarea.getContador());
        tarea.setId(transferTarea.getId());
        try {
            // Se busca al usuario por su id para asignarselo a la tarea
            Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();
            QueryBuilder<Usuario, Integer> uQb = daoUsuario.queryBuilder();
            uQb.where().idEq(transferTarea.getUsuario().getId());
            Usuario usuario = uQb.queryForFirst();
            tarea.setUsuario(usuario);
            // Se actualiza la informacion de la tarea en la BBDD
            Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();
            daoTarea.update(tarea);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarTarea(Integer idTarea) {
        try {
            Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();
            daoTarea.deleteById(idTarea);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deshabilitarTarea(Integer idTarea) {
        try {
            Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();
            Tarea tarea = daoTarea.queryForId(idTarea);
            tarea.setHabilitada(!tarea.getHabilitada());
            daoTarea.update(tarea);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<TransferTarea> consultarTareas(Integer idUsuario) {
        ArrayList<TransferTarea> ret = new ArrayList<TransferTarea>();
        try {
            Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();
            Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();

            // Busca al usuario por su id
            QueryBuilder<Usuario, Integer> uQb = daoUsuario.queryBuilder();
            uQb.where().idEq(idUsuario);
            Usuario usuario = uQb.queryForFirst();
            // Busca las tareas de ese usuario
            QueryBuilder<Tarea, Integer> tQb = daoTarea.queryBuilder();
            tQb.where().eq("USUARIO" , usuario);
            List<Tarea> tareas = tQb.query();

            // Las transformamos en transfers para devolverselas a la vista
            for(int i = 0; i < tareas.size(); i++){
                Tarea tarea = tareas.get(i);
                TransferTarea transfer = new TransferTarea(
                        tarea.getId(), tarea.getTextoAlarma(), tarea.getHoraAlarma(),
                        tarea.getTextoPregunta(), tarea.getHoraPregunta(), tarea.getMejorar(),
                        usuario.convert_transfer(), tarea.getContador(), tarea.getFrecuenciaTarea(),
                        tarea.getNumSi(), tarea.getNumNo(), tarea.getHabilitada());
                transfer.setId(tarea.getId());
                ret.add(i, transfer);
            }
            // En la primera posicion se almacena una tarea fantasma que solo tiene el id de usuario
            // para cuando no haya ninguna
            if(tareas.size() == 0) {
                TransferTarea fantasma = new TransferTarea();
                TransferUsuario usuario_f=new TransferUsuario();
                usuario_f.setId(idUsuario);
                fantasma.setUsuario(usuario_f);
                ret.add(0, fantasma);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public ArrayList<TransferTarea> consultarTareasHabilitadas(Integer idUsuario) {
        ArrayList<TransferTarea> ret = new ArrayList<TransferTarea>();
        try {
            Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();
            Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();

            // Busca al usuario por su id
            QueryBuilder<Usuario, Integer> uQb = daoUsuario.queryBuilder();
            uQb.where().idEq(idUsuario);
            Usuario usuario = uQb.queryForFirst();
            // Busca las tareas de ese usuario
            QueryBuilder<Tarea, Integer> tQb = daoTarea.queryBuilder();
            tQb.where().eq("USUARIO" , usuario).and().eq("HABILITADA", true);
            List<Tarea> tareas = tQb.query();

            // Las transformamos en transfers para devolverselas a la vista
            for(int i = 0; i < tareas.size(); i++){
                Tarea tarea = tareas.get(i);
                TransferTarea transfer = new TransferTarea(
                        tarea.getId(), tarea.getTextoAlarma(), tarea.getHoraAlarma(),
                        tarea.getTextoPregunta(), tarea.getHoraPregunta(), tarea.getMejorar(),
                        usuario.convert_transfer(), tarea.getContador(), tarea.getFrecuenciaTarea(),
                        tarea.getNumSi(), tarea.getNumNo(), tarea.getHabilitada());
                transfer.setId(tarea.getId());
                ret.add(i, transfer);
            }
            // En la primera posicion se almacena una tarea fantasma que solo tiene el id de usuario
            // para cuando no haya ninguna
            if(tareas.size() == 0) {
                TransferTarea fantasma = new TransferTarea();
                TransferUsuario usuario_f=new TransferUsuario();
                usuario_f.setId(idUsuario);
                fantasma.setUsuario(usuario_f);
                ret.add(0, fantasma);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void guardarTareas(List<TransferTarea> tareasUsuario) {
        // Para cada transfer se crea una tarea que se sobreescribe si ya estaba creada en BBDD
        for(int i = 0; i < tareasUsuario.size(); i++){
            TransferTarea transferTarea = tareasUsuario.get(i);
            try {
                //Busco usuario
                Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();
                QueryBuilder<Usuario, Integer> uQb = daoUsuario.queryBuilder();
                uQb.where().idEq(transferTarea.getUsuario().getId());
                Usuario usuario = uQb.queryForFirst();
                // Busco la tarea
                Dao<Tarea, Integer> daoTarea = getHelper().getTareaDao();
                QueryBuilder<Tarea, Integer> tQb = daoTarea.queryBuilder();
                tQb.where().eq("USUARIO" , usuario).and().eq("TEXTO_ALARMA", transferTarea.getTextoAlarma());
                List<Tarea> listaBBDD = tQb.query();
                if(listaBBDD.size() != 0) {
                    Tarea tareaBBDD = listaBBDD.get(0);
                    // Actualizo campos
                    tareaBBDD.setNumSi(transferTarea.getNumSi());
                    tareaBBDD.setNumNo(transferTarea.getNumNo());
                    tareaBBDD.setContador(transferTarea.getContador());
                    tareaBBDD.setFrecuenciaTarea(transferTarea.getFrecuenciaTarea());
                    // Guardo en BBDD
                    daoTarea.update(tareaBBDD);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Evento
	@Override
	public boolean crearEvento(TransferEvento nuevoEvento) {

		try {
			Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
			Evento createE = new Evento();
			createE.setNombreEvento(nuevoEvento.getNombre());
			createE.setHoraAlarma(nuevoEvento.getHoraAlarma());
			createE.setHoraEvento(nuevoEvento.getHoraEvento());
			createE.setFecha(nuevoEvento.getFecha());
			daoEvento.create(createE);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminarEvento(Integer idEvento) {

		try {
			Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
			daoEvento.deleteById(idEvento);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public TransferEvento consultaEvento(TransferEvento consulta) {
		TransferEvento ret = null;
		try {
			Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
			Evento eventoBDD = daoEvento.queryForId(consulta.getId());
			ret = new TransferEvento(eventoBDD.getId(),eventoBDD.getNombreEvento(),
					eventoBDD.getFecha(),eventoBDD.getHoraAlarma(),eventoBDD.getHoraEvento());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean guardarEvento(TransferEvento guardaEvento) {
		try {
			Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
			Evento eventoBDD = daoEvento.queryForId(guardaEvento.getId());
			eventoBDD.setFecha(guardaEvento.getFecha());
			eventoBDD.setHoraAlarma(guardaEvento.getHoraAlarma());
			eventoBDD.setHoraEvento(guardaEvento.getHoraEvento());
			eventoBDD.setNombreEvento(guardaEvento.getNombre());
			daoEvento.update(eventoBDD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ArrayList<TransferEvento> listadoEventos() {
		ArrayList<TransferEvento> ret = new ArrayList<TransferEvento>();
		try {
			Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
			List<Evento> eventosBDD = daoEvento.queryForAll();
			for(int i=0; i < eventosBDD.size(); i++) {
				TransferEvento e = new TransferEvento(eventosBDD.get(i).getId(),eventosBDD.get(i).getNombreEvento(),
						eventosBDD.get(i).getFecha(),eventosBDD.get(i).getHoraAlarma(),eventosBDD.get(i).getHoraEvento());
				ret.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public ArrayList<TransferUsuarioEvento> consultarUsuariosEvento(TransferEvento consultaEvento) {
		ArrayList<TransferUsuarioEvento> ret = new ArrayList<TransferUsuarioEvento>();

		try {
			Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
			Dao<Usuario, Integer> daoUsuario =  getHelper().getUsuarioDao();
			Dao<UsuarioEvento, Integer> daoUsuarioEvento =  getHelper().getUsuarioEventoDao();
			//Consultar Evento
			Evento eventoBDD = daoEvento.queryForId(consultaEvento.getId());

			TransferEvento tEvento = new TransferEvento(eventoBDD.getId(),eventoBDD.getNombreEvento(),eventoBDD.getFecha()
					,eventoBDD.getHoraAlarma(),eventoBDD.getHoraEvento());

			QueryBuilder<UsuarioEvento, Integer> tQb = daoUsuarioEvento.queryBuilder();

			List<UsuarioEvento> eventoUsuarios = tQb.where().eq("EVENTO", eventoBDD).query(); //tendremos todos los eventos con ID_EVENTO

			List<Usuario> usuariosEventos = getHelper().lookupUsuariosForEvento(eventoBDD);
            List<Usuario> usuariosBDD = daoUsuario.queryForAll();
            ret.add(new TransferUsuarioEvento(tEvento,null));//posicion 0 para el evento ( tiene que mostrarse en la vista)
			for(int i = 0; i < usuariosEventos.size(); i++) {
				TransferUsuario tUsuario= new TransferUsuario();
				tUsuario.setId(usuariosEventos.get(i).getId());
				tUsuario.setNombre(usuariosEventos.get(i).getNombre());
				TransferUsuarioEvento tRet = new TransferUsuarioEvento(tEvento,tUsuario);
                tRet.setId(eventoUsuarios.get(i).getId());
				tRet.setActivo(1);
				tRet.setAsistencia(eventoUsuarios.get(i).getAsistencia());
				ret.add(tRet);
			}

			for(int i=0; i < usuariosBDD.size(); i++){
				boolean anyadir =true;
				for(int j=0; j < usuariosEventos.size();j++){
					if(usuariosBDD.get(i).getNombre().equalsIgnoreCase(usuariosEventos.get(j).getNombre()))
						anyadir=false;// Si en algun momento se encuentra en la lista del Evento no lo añado
				}
				if(anyadir){//puedo añadir el objeto i - ESTOS NO ESTAN ACTIVOS
					TransferUsuario tUsuario= new TransferUsuario();
					tUsuario.setId(usuariosBDD.get(i).getId());
					tUsuario.setNombre(usuariosBDD.get(i).getNombre());
					TransferUsuarioEvento tRet = new TransferUsuarioEvento(tEvento,tUsuario);
					tRet.setActivo(0);
					tRet.setAsistencia("NO");//No van a ir obviamente porque no les han invitado
					ret.add(tRet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}


	@Override
	public void crearUsuariosEvento(ArrayList<TransferUsuarioEvento> eventosUsuarios) {
		if(eventosUsuarios.size() > 0) {
			try {
				Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
				Dao<UsuarioEvento, Integer> daoUsuarioEvento = getHelper().getUsuarioEventoDao();
				Dao<Usuario, Integer> daoUsuario =  getHelper().getUsuarioDao();

				QueryBuilder<Evento, Integer> tQb = daoEvento.queryBuilder();
				tQb.where().eq("NOMBRE", eventosUsuarios.get(0).getEvento().getNombre());
				Evento eventoBDD = tQb.queryForFirst();

				for(int i=0; i < eventosUsuarios.size(); i++){
					UsuarioEvento relacion = new UsuarioEvento(eventoBDD, daoUsuario.queryForId(eventosUsuarios.get(i).getUsuario().getId()));
					relacion.setAsistencia("NO");
					daoUsuarioEvento.create(relacion);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


    @Override
    public void guardarUsuariosEvento(ArrayList<TransferUsuarioEvento> eventosUsuarios) {
        if(eventosUsuarios.size() > 0) {
            try {
                Dao<Evento, Integer> daoEvento =  getHelper().getEventoDao();
                Dao<UsuarioEvento, Integer> daoUsuarioEvento = getHelper().getUsuarioEventoDao();
                Dao<Usuario, Integer> daoUsuario =  getHelper().getUsuarioDao();

                QueryBuilder<Evento, Integer> tQb = daoEvento.queryBuilder();
                tQb.where().eq("NOMBRE", eventosUsuarios.get(0).getEvento().getNombre());
                Evento eventoBDD = tQb.queryForFirst(); // Obtenemos el evento


                QueryBuilder<UsuarioEvento, Integer> tQbUsuario_e = daoUsuarioEvento.queryBuilder();

                List<UsuarioEvento> eventoUsuarios = tQbUsuario_e.where().eq("EVENTO", eventoBDD).query(); //tendremos todos los eventos con ID_EVENTO
                daoUsuarioEvento.delete(eventoUsuarios); //Quitamos todos sus usuarios para volverlos añadir

                for(int i=1; i < eventosUsuarios.size(); i++){
                    UsuarioEvento relacion = new UsuarioEvento(eventoBDD, daoUsuario.queryForId(eventosUsuarios.get(i).getUsuario().getId()));
                    relacion.setAsistencia("NO");
                    daoUsuarioEvento.create(relacion);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void crearReto(TransferReto transferReto) {
        try{
            //Hay que asignarselo a ambos
            Dao<Reto, Integer> daoReto = getHelper().getRetoDao();
            Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();

            Usuario usuario = daoUsuario.queryForId(transferReto.getUsuario().getId());

            QueryBuilder<Reto, Integer> rQb = daoReto.queryBuilder();
            rQb.where().eq("USUARIO", usuario);
            Reto reto = rQb.queryForFirst();
            if(reto == null)
                reto = new Reto();

            reto.setTexto(transferReto.getTexto());
            reto.setPremio(transferReto.getPremio());
            reto.setSuperado(transferReto.getSuperado());
            reto.setContador(transferReto.getContador());
            reto.setUsuario(usuario);
            daoReto.createOrUpdate(reto);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TransferReto consultarReto(Integer idUsuario) {
        TransferReto ret = null;

        try {
            Dao<Reto, Integer> daoReto = getHelper().getRetoDao();
            Dao<Usuario, Integer> daoUsuario = getHelper().getUsuarioDao();
            QueryBuilder<Usuario, Integer> uQb = daoUsuario.queryBuilder();
            uQb.where().idEq(idUsuario);
            Usuario usuario = uQb.queryForFirst();

            //Busca el reto de ese usuario
            QueryBuilder<Reto, Integer> rQb = daoReto.queryBuilder();
            rQb.where().eq("USUARIO", usuario);

            Reto r = rQb.queryForFirst();
            ret= new TransferReto();
            ret.setUsuario(usuario.convert_transfer());
            if (r != null)
                ret = new TransferReto(r.getId(), r.getUsuario().convert_transfer(), r.getContador(),
                        r.getTexto(), r.getSuperado(), r.getPremio());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void eliminarReto(Integer idReto) {
        try {
            Dao<Reto, Integer> daoReto = getHelper().getRetoDao();
            daoReto.deleteById(idReto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}