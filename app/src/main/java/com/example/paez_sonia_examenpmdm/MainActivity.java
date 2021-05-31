package com.example.paez_sonia_examenpmdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Http2Reader;

import android.view.View;
import android.widget.Toast;

import com.example.paez_sonia_examenpmdm.adaptadores.MascotasAdapter;
import com.example.paez_sonia_examenpmdm.adaptadores.RecyclerTouchListener;
import com.example.paez_sonia_examenpmdm.database.Edit.Edit_Mascota;
import com.example.paez_sonia_examenpmdm.database.Mascota;
import com.example.paez_sonia_examenpmdm.database.MascotaRepositorio;
import com.example.paez_sonia_examenpmdm.database.add.AddMascota;
import com.example.paez_sonia_examenpmdm.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public  static final String ENLACE="https://dam.org.es/files/enlaces.txt";
    public List<String> listaEnlaces;
    public String[] urls;
    private ActivityMainBinding binding;
    private MascotaRepositorio repositorio;
    private Mascota mascota;
    private MascotasAdapter adapter;
    private RecyclerView recyclerView;
    private MainViewModel mainViewModel;
    public  List<Mascota> listMascota;
    public static int ADDRESERVAREQUEST=1;
    public static int EDITRESERVAREQUEST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        descargaOkHTTP(ENLACE);
        //Cargo el RecyclerView
        adapter= new MascotasAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        //LLamo al View model
        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getAllMascotas().observe(this, new Observer<List<Mascota>>() {
            @Override
            public void onChanged(List<Mascota> mascotas) {
                adapter.setMascotas(mascotas);
                setListMascota(mascotas);
            }

        });

        binding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, binding.recyclerView, new RecyclerTouchListener.ClickListener() {
            //Click Corto modifica la Reserva
            @Override
            public void onClick(View view, int position) {
                Mascota mascotaEditada=listMascota.get(position);
                AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).setPositiveButton("Sí, Cambiar", new DialogInterface.OnClickListener() {
                    /**
                     * This method will be invoked when a button in the dialog is clicked.
                     *
                     * @param dialog the dialog that received the click
                     * @param which  the button that was clicked (ex.
                     *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Edit_Mascota.class);
                        intent.putExtra(Edit_Mascota.EXTRA_EDITID, mascotaEditada.getId());
                        intent.putExtra(Edit_Mascota.EXTRA_EDITNOMBRE, mascotaEditada.getNombre());
                        intent.putExtra(Edit_Mascota.EXTRA_EDITRAZA, mascotaEditada.getRaza());
                        intent.putExtra(Edit_Mascota.EXTRA_EDITEDAD, mascotaEditada.getEdad().toString());
                        intent.putExtra(Edit_Mascota.EXTRA_EDITIMAGEN, mascotaEditada.getImagen());
                        startActivityForResult(intent, EDITRESERVAREQUEST);
                    }
                }).setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .setTitle("Confirmar")
                        .setMessage("¿Modificar a la el paseo  del dia " + mascotaEditada.getNombre() + "?")
                        .create();
                alertDialog.show();
            }
            //Click largo elimina la Reserva
            @Override
            public void onLongClick(View view, int position) {
                final Mascota mascotaEliminar=listMascota.get(position);;
                AlertDialog dialog1 = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainViewModel.Delete(mascotaEliminar);
                                refrescarMascotas();

                                Toast.makeText(MainActivity.this, "Mascota eliminada", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar la mascota " + mascotaEliminar.getNombre() + "?")
                        .create();
                dialog1.show();

            }


        }));

        //Actualizamos el recyclerView
       refrescarMascotas();

        binding.fab.setOnClickListener(this);
    }

    private void refrescarMascotas() {
        if (adapter == null) return;
        List<Mascota> mascotas= getListMascota();
        adapter.setMascotas(mascotas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v== binding.fab){
            Intent intento = new Intent(MainActivity.this, AddMascota.class);
            startActivityForResult(intento,ADDRESERVAREQUEST);
        }

    }

    public List<Mascota> getListMascota() {
        return listMascota;
    }

    public void setListMascota(List<Mascota> listMascota) {
        this.listMascota = listMascota;
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
                  mostrarError(e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    try (ResponseBody responseBody = response.body()) {
                        if (response.isSuccessful()) {
                            String responseData= responseBody.string();
                            Log.e("ENLACE",responseData);
                            urls=responseData.split("\n");
                            for(int i= 0;i< urls.length;i++){
                               String enlace= urls[i];
                               Handler uiHandler = new Handler(Looper.getMainLooper());
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        descargarmascota(enlace);

                                    }
                                });

                          }


                        } else {
                           mostrarMensaje(response.toString());

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
                mostrarError("ERROR"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    Log.e("ENLACE",enlace);
                    if (response.isSuccessful()) {
                        String responseData = responseBody.string();
                        Log.e("REsponsebody", responseData);
                        urls = responseData.split("\n");
                        String nombredescargado = urls[0];
                        String []nombrelimpia=nombredescargado.split(": ");
                        String nombre=nombrelimpia[1];
                        String razadescargada= urls[1];
                        String []razalimpia=razadescargada.split(":");
                        String raza=razalimpia[1];
                        String edaddescargada = urls[2];
                        String []edadlimpia=edaddescargada.split(":");
                        String edadNumero=edadlimpia[1];
                        String []sinEspacio=edaddescargada.split(" ");
                        String edad= sinEspacio[1];
                        String imagenDEscargada = urls[3];
                        String [] imagenLimpia =imagenDEscargada.split(":");
                        String []espacio=imagenLimpia[1].split(" ");
                        String imagen= "https://dam.org.es/"+espacio[1];
                        mascota= new Mascota();
                        mascota.setNombre(nombre);
                        Log.e("mascota",mascota.getNombre());
                        mascota.setRaza(raza);
                        Log.e("mascota",mascota.getRaza());
                        mascota.setEdad(Integer.valueOf(edad));
                        Log.e("mascota",mascota.getEdad().toString());
                        mascota.setImagen(imagen);
                        Log.e("mascota",mascota.getImagen());
                        Log.e("mascota",mascota.toString());
                        mainViewModel.Insert(mascota);
                    } else {
                        Log.e("Response bodyERRor",responseBody.toString());
                        mostrarMensaje(response.toString());

                    }

                }
            }
        });
    }

    private void mostrarError(String s) {
        Looper.prepare();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    private void mostrarMensaje(String s) {
        Looper.prepare();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resibimos datos de la Clase ADD Reserva
        if (requestCode == ADDRESERVAREQUEST && resultCode == RESULT_OK) {// Si sale bien el intent recogemos los datos
            String nombre = data.getStringExtra(AddMascota.EXTRA_ADDNOMBRE);
            String raza = data.getStringExtra(AddMascota.EXTRA_ADDRAZA);
            String edad = data.getStringExtra(AddMascota.EXTRA_ADDEDAD);
            String imagen = data.getStringExtra(AddMascota.EXTRA_ADDIMAGEN);
            mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setEdad(Integer.valueOf(edad));
            mascota.setRaza(raza);
            mascota.setImagen(imagen);


        } else if (requestCode == EDITRESERVAREQUEST && resultCode == RESULT_OK) {// confirmamos los datos de lamodificacion y si lo cumple modificamoe el registro
            Integer id = data.getIntExtra(Edit_Mascota.EXTRA_EDITID, -1);
            if (id == -1) {
                Toast.makeText(this, "Paseo no se puede modificar",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String nombreEdit = data.getStringExtra(Edit_Mascota.EXTRA_EDITNOMBRE);
            Log.e("nombre", nombreEdit);
            String razaEdit = data.getStringExtra(Edit_Mascota.EXTRA_EDITRAZA);
            Log.e("raza", razaEdit);
            String edadEdit = data.getStringExtra(Edit_Mascota.EXTRA_EDITEDAD);
            Log.e("Edad", edadEdit);
            String imagenEdit = data.getStringExtra(Edit_Mascota.EXTRA_EDITIMAGEN);
            Log.e("HORAAÑ", imagenEdit);

            mascota = new Mascota();
            mascota.setId(id);
            mascota.setNombre(nombreEdit);
            mascota.setRaza(razaEdit);
            mascota.setEdad(Integer.valueOf(edadEdit));
            mascota.setImagen(imagenEdit);

        }
    }

}
