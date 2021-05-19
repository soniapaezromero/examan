package com.example.paez_sonia_examenpmdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public  static final String ENLACE="https://dam.org.es/files/enlaces.txt";
    public List<String> listaEnlaces;
    public String[] urls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descargaOkHTTP(ENLACE);
    }
    private void descargaOkHTTP(String uri) {
            URL web = null;
            try {
                web = new URL(uri);
            } catch (MalformedURLException e) {
                mostrarError("Fallo: " + e.getMessage());
            }
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(web)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("ERROR PRUEBA",e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    try (ResponseBody responseBody = response.body()) {
                        if (response.isSuccessful()) {
                            String responseData= responseBody.string();
                            Log.e("REsponsebody", responseData);
                            urls=responseData.split("\n");
                            for(int i= 0;i< urls.length;i++){
                               String enlace= urls[i];
                               descargarmascota(enlace);
                          }


                            Handler uiHandler = new Handler(Looper.getMainLooper());
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        } else {
                            Log.e( "DESCARGADO",response.toString());

                        }
                    }
                }
            });

        }

    private void descargarmascota(String enlace) {
        URL web = null;
        try {
            web = new URL(enlace);
        } catch (MalformedURLException e) {
            mostrarError("Fallo: " + e.getMessage());
        }
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(web)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                        String responseData = responseBody.string();
                        Log.e("REsponsebody", responseData);
                        urls = responseData.split("\n");
                        String nombre = urls[0];
                        String raza = urls[1];
                        String edad = urls[2];
                        String imagen = urls[3];
                        Log.e("MAscota descargada", nombre);

                    } else {
                        Log.e("DESCARGADO", response.toString());

                    }

                }
            }
        });
    }

    private void mostrarError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}

