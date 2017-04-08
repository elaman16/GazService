package kz.elaman.gazservice.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kz.elaman.gazservice.R;
import kz.elaman.gazservice.utils.AppController;


public class NetworkManager extends AppController {
    private static final String TAG = "NetworkManager";
    private static NetworkManager instance = null;


    //for Volley API
    public RequestQueue requestQueue;
    private ImageLoader mImageLoader;

    Context context;

    private NetworkManager(Context mContext) {
        context = mContext;
        getRequestQueue();

        //other stuf if you need
    }

    public static synchronized NetworkManager getInstance(Context mContext) {
        if (null == instance)
            instance = new NetworkManager(mContext);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.requestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public void doVolleyGetRequest(final Context context, String URL, final VolleyResultListener<String> listener) {

        JsonRequest request = new JsonArrayRequest(Request.Method.GET, URL, new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (null != response.toString())
                            listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);


                            if (error instanceof TimeoutError){
                                toastShow(context, context.getResources().getString(R.string.no_internet));
                                listener.getResult("");
                            }
                            else {
                                toastShow(context, "Ошибка сервера, попробуйте еще раз");
                            }
                        }
                    }
                }){
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

    // Пока POST запрос жасағанша осылай қалдырамын

    public void doVolleyPostRequest(final Context context, String URL, final VolleyResultListener<String> listener) {

        Map<String,String> params = new HashMap<String,String>();
        params.put("UserName", "center");
        params.put("Password ","123456");
        JSONObject jsonObj = new JSONObject(params);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("UserName", "center");
            jsonBody.put("Password ", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            if (error instanceof TimeoutError){
                                toastShow(context, context.getResources().getString(R.string.no_internet));
                                listener.getResult("");
                            }
                            else {
                                toastShow(context, "Ошибка сервера, попробуйте еще раз");
                            }
                        }
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            /*
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("UserName", "center");
                    jsonBody.put("Password ", "123456");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes();
            }*/


            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json; charset=utf-8");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("UserName", "center");
                params.put("Password ","123456");
                return params;
            }*/
        };
        /*request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        getRequestQueue().add(request);

    }


    public void addToRequestQueue(JsonObjectRequest objectRequest) {
        getRequestQueue().add(objectRequest);
    }

    public void addToRequestQueue(StringRequest stringRequest) {
        getRequestQueue().add(stringRequest);
    }


    public interface VolleyResultListener<T> {
        void getResult(T object);
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static String convertImgURL(String url) {
        if (url != null) {
            url = "http://kuda-poydem.kz/" + url;
            url = url.replace("~/", "/");
            url = url.replace(" ", "%20");
        }
        return url;
    }

    /**
     * Конвертирунт JsonArray в StringArray
     * @param jsonArray
     * @return stringArray
     */
    public static String[] convertArray(JSONArray jsonArray){
        String[] stringArray = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                stringArray[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return stringArray;
    }

    public static void toastShow(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();

    }
}
