package kz.elaman.gazservice.utils;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

import kz.elaman.gazservice.network.NetworkManager;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance(this);

        Locale locale = new Locale("ru_RU");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
    }


}