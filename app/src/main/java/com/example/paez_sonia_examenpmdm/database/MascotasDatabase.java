package com.example.paez_sonia_examenpmdm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Mascota.class}, version = 1)
public abstract class MascotasDatabase extends RoomDatabase {
    private static MascotasDatabase INSTANCE;
    public abstract MascotaDao mascotaDao();
    public static MascotasDatabase getInstance(Context context){
        if(INSTANCE== null) {
            synchronized (MascotasDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MascotasDatabase.class, "mascotas")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MascotaDao mascotaDao;
        PopulateDbAsync(MascotasDatabase db){
            mascotaDao= db.mascotaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }



}
