package com.andima.gestordeapps;

import android.view.Menu;

/**
 * Created by Andima on 29/03/2018.
 */

public class ListaAppsTodasActivity extends ListaAppsAbstractActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        /*se pone a true la visibilidad de los elementos del menú
         * que deben mostrarse en esta actividad*/
        menu.findItem(R.id.appsUsuario).setVisible(true);
        return result;
    }

    @Override
    protected void onResume() {

        super.onResume();

        //se recarga la lista de apps por si hubiera cambios.
        CargarAppsAsyncTask tarea = new CargarAppsAsyncTask(this);
        tarea.setTodas(true);
        tarea.setIncluirInstaladas(true);
        tarea.execute();
    }
}
