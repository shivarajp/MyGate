package com.shivaraj.mygate.model.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "users_table")
public class UserModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;


    @ColumnInfo(name = "pass_code")
    private String passCode;

    @ColumnInfo(name = "url")
    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserModel(){};

    public UserModel(String name, String passcode, String url) {
        this.name = name;
        this.passCode = passcode;
        this.url = url;
    }
}
