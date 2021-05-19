package com.example.paez_sonia_examenpmdm.database.Edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.paez_sonia_examenpmdm.R;
import com.example.paez_sonia_examenpmdm.database.Mascota;
import com.example.paez_sonia_examenpmdm.database.MascotaRepositorio;
import com.example.paez_sonia_examenpmdm.databinding.ActivityAddMascotaBinding;
import com.example.paez_sonia_examenpmdm.databinding.ActivityEditMascotaBinding;

public class Edit_Mascota extends AppCompatActivity implements View.OnClickListener{
    private ActivityEditMascotaBinding bindingedit;
    private  MascotaRepositorio repositorio;

    private Mascota mascota;
    public static final String  EXTRA_EDITID="com.example.paez_sonia_examenpmdm.database.Edit.EXTRA_EDITID";
    public static final String EXTRA_EDITNOMBRE = "com.example.paez_sonia_examenpmdm.database.Edit.EXTRA_EDITNOMBRE";
    public static final String EXTRA_EDITRAZA = "com.example.paez_sonia_examenpmdm.database.Edit.EXTRA_EDITRAZA";
    public static final String EXTRA_EDITEDAD = "com.example.paez_sonia_examenpmdm.database.Edit.EXTRA_EDITEDAD";
    public static final String EXTRA_EDITIMAGEN = "com.example.paez_sonia_examenpmdm.database.Edit.EXTRA_EDITIMAGEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__mascota);
        bindingedit = ActivityEditMascotaBinding.inflate(getLayoutInflater());
        View view = bindingedit.getRoot();
        setContentView(view);
        // setTitle("Modificar reserva");
        Intent intent= getIntent();
        if(intent.hasExtra(EXTRA_EDITID)) {
            setTitle("Modificar mascota");
            Log.e("nombre", String.valueOf(intent.getIntExtra(EXTRA_EDITID,-1)));
            bindingedit.addnombre.setText(intent.getStringExtra(EXTRA_EDITNOMBRE));
            Log.e("raza", intent.getStringExtra(EXTRA_EDITRAZA));
            bindingedit.addRaza.setText(intent.getStringExtra(EXTRA_EDITRAZA));
            Log.e("edad", intent.getStringExtra(EXTRA_EDITEDAD));
            bindingedit.addEdad.setText(intent.getStringExtra(EXTRA_EDITEDAD));
            Log.e("imagen", intent.getStringExtra(EXTRA_EDITIMAGEN));
            bindingedit.addImagen.setText(intent.getStringExtra(EXTRA_EDITIMAGEN));
             bindingedit.botonAdd.setOnClickListener(this);
             bindingedit.botonBorrar.setOnClickListener(this);
        }


    }



    @Override
    public void onClick(View v) {
        if (v == bindingedit.botonAdd) {
           modificarmascota();
        }
        if (v == bindingedit.botonBorrar) {
            finish();
        }

    }

    private void modificarmascota() {
        bindingedit.addnombre.setError(null);
        bindingedit.addRaza.setError(null);
        bindingedit.addEdad.setError(null);
        bindingedit.addImagen.setError(null);
        String nombre = bindingedit.addnombre.getText().toString();
        String raza = bindingedit.addRaza.getText().toString();
        String edad = bindingedit.addEdad.getText().toString();
        String imagen=bindingedit.addImagen.getText().toString();;
        if ("".equals(nombre)) {
            bindingedit.addnombre.setText("Tienes que introducir el dato");
            bindingedit.addnombre.requestFocus();
            return;
        }
        if ("".equals(raza)) {
            bindingedit.addRaza.setText("Tienes que introducir el dato");
            bindingedit.addRaza.requestFocus();
            return;
        }
        if ("".equals(edad)) {
            bindingedit.addEdad.setText("Tienes que introducir el dato");
            bindingedit.addEdad.requestFocus();
            return;

        }
        Intent datoEdit=new Intent();
        datoEdit.putExtra(EXTRA_EDITNOMBRE,nombre);
        datoEdit.putExtra(EXTRA_EDITRAZA,raza);
        datoEdit.putExtra(EXTRA_EDITEDAD,edad);
        datoEdit.putExtra(EXTRA_EDITIMAGEN,imagen);

        Integer id=getIntent().getIntExtra(EXTRA_EDITID,-1);
        Log.e("DAtos Enviados ID",String.valueOf(id));
        if(id!= -1) {
            datoEdit.putExtra(EXTRA_EDITID,id);
        }
        setResult(RESULT_OK, datoEdit);
        Log.e("DAtos Enviados","Editar encia datos");
        finish();


    }
}