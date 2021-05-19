package com.example.paez_sonia_examenpmdm.database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MascotaRepositorio {
    @SuppressLint("StaticFieldLeak")
    public  MascotaDao mascotaDao;
    private LiveData<List<Mascota>> allMascotas;

    public MascotaRepositorio(Application aplication) {
        MascotasDatabase mascotasDatabase = MascotasDatabase.getInstance(aplication);
        mascotaDao = mascotasDatabase.mascotaDao();
        allMascotas=mascotaDao.getMascotas();


    }

    public  LiveData<List<Mascota>> getAllMascotas() {
        return allMascotas;
    }

    public LiveData<Mascota> getMascota(Integer id) {
        return mascotaDao.getMascota(id);
    }

    public void addMascota(Mascota mascota) {
        new InsertAsynctask(mascotaDao).execute(mascota);
    }

    public void updateReserva(Mascota mascota) {
        new UpdateAsynctask(mascotaDao).execute(mascota);
    }

    public void deleteReserva(Mascota mascota) {
        new DeleteAsynctask(mascotaDao).execute(mascota);
        ;
    }
    // Asyntak para crear registro
    private static class InsertAsynctask extends AsyncTask<Mascota, Void, Void> {
       MascotaDao mascotaDao;

        public InsertAsynctask(MascotaDao mascotaDao) {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas) {
            mascotaDao.addMascota(mascotas[0]);
            return null;
        }
    }
    // Asyntak para actualizar registro
    private static class UpdateAsynctask extends AsyncTask<Mascota, Void, Void> {
        MascotaDao mascotaDao;

        public UpdateAsynctask(MascotaDao mascotaDao) {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas) {
            mascotaDao.updateMascota(mascotas[0]);
            return null;
        }
    }
    // Asyntak para borrar registro
    private static class DeleteAsynctask extends AsyncTask<Mascota, Void, Void> {
        MascotaDao mascotaDao;

        public DeleteAsynctask(MascotaDao mascotaDao) {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas) {
            mascotaDao.deleteMascota(mascotas[0]);
            return null;
        }
    }
}



