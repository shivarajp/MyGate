package com.shivaraj.mygate.model.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase INSTANCE;

    public abstract UsersDao usersDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };

    public static UserDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "users")
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallback)
                    .build();

        }
        return INSTANCE;
    }

}
