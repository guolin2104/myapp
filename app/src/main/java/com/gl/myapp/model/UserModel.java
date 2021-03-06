package com.gl.myapp.model;

/**
 * Created by zft on 2019/1/7.
 */
public class UserModel implements IUser {

    private String name;
    private String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String password) {
        if (name == null || password == null || !name.equals(getName()) || !password.equals(getPassword())) {
            return -1;
        }
        return 0;
    }
}
