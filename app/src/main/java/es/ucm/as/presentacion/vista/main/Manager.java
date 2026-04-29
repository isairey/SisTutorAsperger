package es.ucm.as.presentacion.vista.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;


public class Manager {
    private static Manager ourInstance = new Manager();
    private AppCompatActivity activity;

    // Metodo que se utiliza desde los servicios de aplicacion
    public static Manager getInstance() {
        return ourInstance;
    }
    // Constructor privado del singleton
    private Manager() { }
    // Metodo que cambia la activity, se utiliza desde las propias activities
    public void setActivity(AppCompatActivity miActivity){
        activity = miActivity;
    }

    public Activity getActivity(){
        return activity;
    }
    // Metodo que devuelve el context, se utiliza desde los servicios de aplicacion
    public Context getContext(){
        return activity.getApplicationContext();
    }

    // Metodo que devuelve el FragmentManager, se utiliza desde el dispatcher
    public android.support.v4.app.FragmentManager getFragmentManager(){
        return activity.getSupportFragmentManager();
    }
}