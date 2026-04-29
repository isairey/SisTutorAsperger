package es.ucm.as.presentacion.controlador;


import es.ucm.as.presentacion.controlador.imp.DispatcherImp;

public abstract class Dispatcher  {
    private static Dispatcher dispatcher;

    public static Dispatcher getInstancia() {
        if (dispatcher == null){
            dispatcher = new DispatcherImp();
        }
        return dispatcher;
    }
    public abstract void dispatch(String comando,Object datos);//el comando será el que este definido en ListaComandos.java
}
