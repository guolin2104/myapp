package com.gl.myapp.model;

/**
 * Created by zft on 2019/1/7.
 */

public interface IUser {


    String getName();

    String getPassword();

    int checkUserValidity(String name, String password);
}
