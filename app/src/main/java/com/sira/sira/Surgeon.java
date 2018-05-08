package com.sira.sira;

/**
 * Created by lea on 05.04.18.
 */

public class Surgeon {
    private String initial;
    private String surname;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String lastname;
    private String title;

    public Surgeon (String initial, String surname, String lastname, String title){
        this.initial = initial;
        this.surname = surname;
        this.lastname = lastname;
        this.title = title;
    }

    public Surgeon(){

    }


}
