package fr.epita.android.pri;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Rohit on 11/3/2017.
 */

public class Relation implements Serializable{

    int id, sexe;
    String mob, email,name,login,pass, dob;
    Uri uri;
    private boolean isLogged = false;

    public void setId(int id)
    {
        this.id=id;
    }

    public int getId()
    {
        return id;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getMob() {
        return mob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setLogged(boolean isLogged) { this.isLogged = isLogged; }

    public boolean getLogged() { return this.isLogged; }

    public Uri getUri() { return this.uri; }

    public void setUri(Uri uri) { this.uri = uri; }

    public String getDob() { return this.dob; }

    public void setDob(String date) { this.dob = date; }

    public int getSexe() { return this.sexe; }

    public void setSexe(int s) { this.sexe = s; }
}
