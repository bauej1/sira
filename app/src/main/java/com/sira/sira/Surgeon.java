package com.sira.sira;

/**
 * Created by lea on 05.04.18.
 */

public class Surgeon {
    private String initial;
    private String surname;
    private String lastname;
    private String title;

    public Surgeon (String initial, String surname, String lastname, String title){
        this.initial = initial;
        this.surname = surname;
        this.lastname = lastname;
        this.title = title;
    }

    public String getInitial(){
        return initial;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTitle() {
        return title;
    }
}
