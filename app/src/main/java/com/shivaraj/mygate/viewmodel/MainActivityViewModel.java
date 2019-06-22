package com.shivaraj.mygate.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;

import com.shivaraj.mygate.model.UserRepository;
import com.shivaraj.mygate.model.local.UserModel;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private UserRepository repository;
    private MutableLiveData<List<UserModel>> users = new MutableLiveData();

    public MainActivityViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<List<UserModel>> getUsers(){
        return repository.loadAllUsersFromDb();
    }

    public LiveData<List<UserModel>> getSingleUser(){
        return repository.loadAllUsersFromDb();
    }

    public void saveUserToDb(UserModel newUser) {
        new LongOperation().execute(newUser);
    }

    private class LongOperation extends AsyncTask<UserModel, Void, Void> {

        @Override
        protected Void doInBackground(UserModel... params) {
                try {
                    repository.saveNewUser(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
