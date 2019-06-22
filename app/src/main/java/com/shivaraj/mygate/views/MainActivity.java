package com.shivaraj.mygate.views;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shivaraj.mygate.R;
import com.shivaraj.mygate.databinding.ActivityMainBinding;
import com.shivaraj.mygate.model.local.UserModel;
import com.shivaraj.mygate.util.FileHelper;
import com.shivaraj.mygate.viewmodel.MainActivityViewModel;
import com.shivaraj.mygate.views.adapters.UsersAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private static final int MY_CAMERA_PERMISSION_CODE = 122;
    private static final int CAMERA_REQUEST = 2;
    private MainActivityViewModel viewModel;
    private UsersAdapter mAdapter;
    private List<UserModel> mUsers = new ArrayList<>();
    private ActivityMainBinding mBinding;
    private String imageFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpViewModel();
        mBinding.setMainActivity(this);
        init();
        subscribe();
    }

    public void addNewUser() {

        checkPermissionAndStartCamera();
    }

    private void checkPermissionAndStartCamera() {

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            openCameraIntent();
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = FileHelper.createImageFile(this);
                imageFilePath = photoFile.getAbsolutePath();

                Log.d("paaath", imageFilePath);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.shivaraj.mygate.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                imageFilePath = photoURI.toString();
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSION_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            int max = 999999;
            int min = 111111;
            int randomNum = ThreadLocalRandom.current().nextInt(0, max+1);

            //got the image file now tell viewmodel to store it.
            UserModel newUser = new UserModel("name", String.format("%d", randomNum), imageFilePath);

            viewModel.saveUserToDb(newUser);
        }
    }

    private void subscribe() {
        viewModel.getUsers().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(@Nullable List<UserModel> users) {
                //mUsers = users;
                mAdapter.setJobsList(users);
            }
        });
    }

    private void init() {
        mAdapter = new UsersAdapter(mUsers);
        mBinding.usersRv.setLayoutManager(new LinearLayoutManager(this));
        mBinding.usersRv.setAdapter(mAdapter);
    }

    private void setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }
}
