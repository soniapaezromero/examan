package com.example.paez_sonia_examenpmdm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paez_sonia_examenpmdm.database.Mascota;
import com.example.paez_sonia_examenpmdm.database.MascotaRepositorio;

import java.util.List;

 public  class MainViewModel extends AndroidViewModel {
    private MascotaRepositorio repositorio;
    private LiveData<List<Mascota>> todasmascotas;
    public MainViewModel(Application application) {
        super(application);
        repositorio= new MascotaRepositorio(application);
        todasmascotas = repositorio.getAllMascotas();

    }

    public void Insert(Mascota mascota){// Inserta una Reserva en la bse de datos
        repositorio.addMascota(mascota);
    }
    public void Update(Mascota mascota){// Modifica la vbase de datos
        repositorio.updateReserva(mascota);
    }
    public void Delete(Mascota mascota){// Borra registro
        repositorio.deleteReserva(mascota);
    }
    public  LiveData<List<Mascota
            >> getAllMascotas(){// Muestra todads las Reservas
        return todasmascotas;
    }


}
