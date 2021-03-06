package kz.elaman.gazservice.login;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import kz.elaman.gazservice.MainActivity;
import kz.elaman.gazservice.R;
import kz.elaman.gazservice.network.NetworkManager;
import kz.elaman.gazservice.utils.Constants;
import kz.elaman.gazservice.utils.PrefHelper;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button btnLogin, btnRegistration;
    EditText mEmailField;
    EditText mPasswordField;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(R.string.enter_to_profile);

        // Views
        mEmailField = (EditText) findViewById(R.id.login);
        mPasswordField = (EditText) findViewById(R.id.pass);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_login);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnRegistration = (Button) findViewById(R.id.buttonRegistration);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                    if (user2 != null) {
                        // Name, email address, and profile photo Url
                        String name = user2.getDisplayName();
                        String email = user2.getEmail();
                        Uri photoUrl = user2.getPhotoUrl();

                        // Check if user's email is verified
                        //boolean emailVerified = user.isEmailVerified();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        String uid = user2.getUid();
                    }

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    new PrefHelper(LoginActivity.this).setUserId(user.getUid());
                    new PrefHelper(LoginActivity.this).setUserName(user.getDisplayName());
                    new PrefHelper(LoginActivity.this).setUserEmail(user.getEmail());
                    new PrefHelper(LoginActivity.this).setUserImg(String.valueOf(user.getPhotoUrl()));

                    startMainActivity();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        };

        setClickEvents();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }


                });
        // [END sign_in_with_email]
    }

    private void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Обязательное поле.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Обязательное поле.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
    private void doServerRequest(String API_URL) {
        if (NetworkManager.haveNetworkConnection(this)) {
            NetworkManager.getInstance(this).doVolleyPostRequest(this, API_URL, new NetworkManager.VolleyResultListener<String>() {
                @Override
                public void getResult(String result) {

                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            internetOff();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void internetOff() {
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "Нет интернет соединения!", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Loading
                    }
                });
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setDuration(15000);
        snackbar.show();

    }

    String finalResult = "";

    public String postData(String... data) {



        HttpClient httpclient = new DefaultHttpClient();
        // specify the URL you want to post to
        HttpPost httppost = new HttpPost(Constants.API_LOGIN);
        try {
            // create a list to store HTTP variables and their values
            List nameValuePairs = new ArrayList();
            // add an HTTP variable and value pair
            nameValuePairs.add(new BasicNameValuePair("username", data[0]));
            nameValuePairs.add(new BasicNameValuePair("password", data[1]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // send the variable and value, in other words post, to the URL
            HttpResponse httpResponse = httpclient.execute(httppost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String bufferedStrChunk = null;

            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedStrChunk);
            }
            finalResult = stringBuilder.toString();


        } catch (ClientProtocolException e) {
            // process execption
        } catch (IOException e) {
            // process execption
        }
        return finalResult;
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            return postData(params[0], params[1]);
        }

        protected void onPostExecute(String result) {

            progressBar.setVisibility(View.GONE);

            try {
                JSONObject jsonObject = new JSONObject(result);
                new PrefHelper(LoginActivity.this).setUserId(jsonObject.getString("userid"));
                new PrefHelper(LoginActivity.this).setUserId(jsonObject.getString("role"));

//                if (jsonObject.getString("role").equals("Администратор"))
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    private void setClickEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
}
