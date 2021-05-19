package com.example.paez_sonia_examenpmdm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paez_sonia_examenpmdm.database.Mascota;
import com.example.paez_sonia_examenpmdm.database.MascotaRepositorio;

import java.util.List;

 public  class MAinviewModel extends AndroidViewModel {
    private MascotaRepositorio repositorio;
    private LiveData<List<Mascota>> todasReservas;
    public MAinviewModel(Application application) {
        super(application);
        repositorio= new MascotaRepositorio(application);
        todasReservas= repositorio.getAllMascotas();

    }

    public void Insert(Mascota reserva){// Inserta una Reserva en la bse de datos
        repositorio.addMascota(reserva);
    }
    public void Update(Mascota reserva){// Modifica la vbase de datos
        repositorio.updateReserva(reserva);
    }
    public void Delete(Mascota reserva){// Borra registro
        repositorio.deleteReserva(reserva);
    }
    public  LiveData<List<Mascota
            >> getAllReservas(){// Muestra todads las Reservas
        return todasReservas;
    }


}
