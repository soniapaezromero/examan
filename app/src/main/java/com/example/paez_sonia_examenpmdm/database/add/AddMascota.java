package com.example.paez_sonia_examenpmdm.database.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.paez_sonia_examenpmdm.R;
import com.example.paez_sonia_examenpmdm.database.Mascota;
import com.example.paez_sonia_examenpmdm.database.MascotaRepositorio;
import com.example.paez_sonia_examenpmdm.databinding.ActivityAddMascotaBinding;

public class AddMascota extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddMascotaBinding bindingAdd;
    private MascotaRepositorio repositorio;

    private Mascota mascoata;
    public static final String EXTRA_ADDNOMBRE = "com.example.paez_sonia_examenpmdm.database.add.EXTRA_ADDNOMBRE";
    public static final String EXTRA_ADDRAZA = "com.example.paez_sonia_examenpmdm.database.add.EXTRA_ADDRAZA";
    public static final String EXTRA_ADDEDAD = "com.example.paez_sonia_examenpmdm.database.add.EXTRA_ADDEDAD";
    public static final String EXTRA_ADDIMAGEN = "com.example.paez_sonia_examenpmdm.database.add.EXTRA_ADDiMAGEN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mascota);
        bindingAdd = ActivityAddMascotaBinding.inflate(getLayoutInflater());
        View view = bindingAdd.getRoot();
        setContentView(view);
        setTitle("Añadir Reserva");
        bindingAdd.botonAdd.setOnClickListener(this);
        bindingAdd.botonBorrar.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == bindingAdd.botonAdd) {
            crearMascota();
        }
        if (v == bindingAdd.botonBorrar) {
            finish();
        }

    }

    private void crearMascota() {
        bindingAdd.addnombre.setError(null);
        bindingAdd.addRaza.setError(null);
        bindingAdd.addEdad.setError(null);
        bindingAdd.addImagen.setError(null);
        String nombre = bindingAdd.addnombre.getText().toString();
        String raza = bindingAdd.addRaza.getText().toString();
        String edad = bindingAdd.addEdad.getText().toString();
        String imagen=bindingAdd.addImagen.getText().toString();;
        if ("".equals(nombre)) {
            bindingAdd.addnombre.setText("Tienes que introducir el dato");
            bindingAdd.addnombre.requestFocus();
            return;
        }
        if ("".equals(raza)) {
            bindingAdd.addRaza.setText("Tienes que introducir el dato");
            bindingAdd.addRaza.requestFocus();
            return;
        }
        if ("".equals(edad)) {
            bindingAdd.addEdad.setText("Tienes que introducir el dato");
            bindingAdd.addEdad.requestFocus();
            return;

        }
        Intent datoAdd=new Intent();
        datoAdd.putExtra(EXTRA_ADDNOMBRE,nombre);
        datoAdd.putExtra(EXTRA_ADDRAZA,raza);
        datoAdd.putExtra(EXTRA_ADDEDAD,edad);
        datoAdd.putExtra(EXTRA_ADDIMAGEN,imagen);
        setResult(RESULT_OK,datoAdd);
        finish();


    }
}