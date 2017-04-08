package kz.elaman.gazservice.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import kz.elaman.gazservice.MainActivity;
import kz.elaman.gazservice.R;
import kz.elaman.gazservice.utils.PrefHelper;


public class SplashActivity extends AppCompatActivity {


    private static final long INTERVAL2 = 2000;
    Handler hand = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    hand.postDelayed(run1, INTERVAL2);

                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                }

                return msg;
            }

        }.execute(null, null, null);

    }

    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            if (!new PrefHelper(SplashActivity.this).getUserId().equals("-1")) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

        }
    };
}
