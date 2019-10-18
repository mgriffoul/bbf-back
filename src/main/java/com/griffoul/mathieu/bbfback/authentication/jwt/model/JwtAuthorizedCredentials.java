package com.griffoul.mathieu.bbfback.authentication.jwt.model;

import java.io.Serializable;

public class JwtAuthorizedCredentials implements Serializable {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
