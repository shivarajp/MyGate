package com.shivaraj.mygate.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.shivaraj.mygate.model.local.UserDatabase;
import com.shivaraj.mygate.model.local.UserModel;
import com.shivaraj.mygate.model.local.UsersDao;

import java.util.List;

public class UserRepository {
    final private UsersDao dao;


    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.dao = db.usersDao();
    }

    public LiveData<List<UserModel>> loadAllUsersFromDb() {
        return dao.getAllUser();
    }

    public LiveData<UserModel> loadSnigleUserFromDb(String passCode) {
        return dao.getUser(passCode);
    }

    public void saveNewUser(UserModel newUser) {
        dao.insertAll(newUser);
    }
}
