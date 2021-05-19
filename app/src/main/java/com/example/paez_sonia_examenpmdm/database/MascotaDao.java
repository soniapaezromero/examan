package com.example.paez_sonia_examenpmdm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract interface MascotaDao {
    @Transaction// Ordena la base por fehas dela mas reciente a la mas antigue
    @Query(value = "SELECT * FROM mascotas")
    LiveData<List<Mascota>> getMascotas();
    @Transaction
    @Query("SELECT * FROM mascotas WHERE id LIKE :id")
    LiveData <Mascota> getMascota(Integer id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)//Inserta datos en la tabla
    void addMascota(Mascota mascota);

    @Delete
    void deleteMascota(Mascota mascota);// Borra un registro de la tabal

    @Update
    void updateMascota(Mascota mascota);// Actualiza el registro

}
