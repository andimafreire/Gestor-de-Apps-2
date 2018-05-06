package com.andima.gestordeapps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.List;

/**
 * Created by Andima on 14/03/2018.
 */

public abstract class ListaAppsAbstractActivity extends AbstractActivity{

    private List<App> apps;
    protected RecyclerView listaApps;
    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_apps);
        listaApps = findViewById(R.id.lista);

        /*si la aplicación sufre un reinicio forzado reabre la actividad sin pasar por la actividad
        * principal*/
        if (VariablesGlobales.getVariablesGlobales().getEmail()==null) {
            VariablesGlobales.getVariablesGlobales().setEmail(PreferenceManager
                    .getDefaultSharedPreferences(this).getString("USUARIO", null));
        }
        mProgressView = findViewById(R.id.lista_progress);
        mProgressView.bringToFront();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //si se ha cerrado sesión o se ha eliminado la cuenta la actividad termina
        if(VariablesGlobales.getVariablesGlobales().getEmail()==null)
            finish();
        /* Se comprueba si el tema ha sido cambiado y
         * se relanza la actividad para aplicarlo*/
        Boolean esOscuro = getWindow().getStatusBarColor() == getResources().getColor(R.color.statusBarColor);
        if((esOscuro && PreferenceManager.getDefaultSharedPreferences(this).
                getString("PREFERENCIA_DE_TEMA", "claro").equals("claro"))||
                (!esOscuro && PreferenceManager.getDefaultSharedPreferences(this).
                        getString("PREFERENCIA_DE_TEMA", "claro").equals("oscuro"))){
            recreate();
            //si esta Actividad está abierta AppInfoActivity no se encuentra en la pila
            VariablesGlobales.getVariablesGlobales().setTemaCambiado(false);
        }
        showProgress(true);

    }

    private void cargarRecyclerView(){
        ListaAppsRecyclerAdapter elAdapter = new ListaAppsRecyclerAdapter(apps);
        listaApps.setAdapter(elAdapter);
        LinearLayoutManager elLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listaApps.setLayoutManager(elLayoutManager);
        elAdapter.setActivity(this);
        showProgress(false);
    }

    //carga la lista de apps procesada en CargarAppsAsyncTask
    public void cargarApps(List<App> pApps){
        apps = pApps;
        cargarRecyclerView();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}