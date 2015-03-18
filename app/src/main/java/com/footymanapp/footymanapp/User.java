package com.footymanapp.footymanapp;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;


public class User {
    private int id;
    private String username;
    private String password;

    public User(String uname, String pword)
    {
        username = uname;
        password = pword;
    }
}
