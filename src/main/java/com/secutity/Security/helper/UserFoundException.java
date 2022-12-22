package com.secutity.Security.helper;

public class UserFoundException extends Exception {

    public UserFoundException()
    {
        super("User with this username is already there in db!!try again with another one");

    }
    public UserFoundException(String msg)
    {
        super(msg);
    }
}