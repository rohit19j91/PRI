package fr.epita.android.pri;

/**
 * Created by Rohit on 11/3/2017.
 */

public class Relation {

    int id,mob;
    String email,name,login,pass,confpass;

    public void setId(int id)
    {
        this.id=id;
    }

    public int getId()
    {
        return id;
    }

    public void setMob(int mob) {
        this.mob = mob;
    }

    public int getMob() {
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

    public void setConfpass(String confpass) {
        this.confpass = confpass;
    }

    public String getConfpass() {
        return confpass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }
}
