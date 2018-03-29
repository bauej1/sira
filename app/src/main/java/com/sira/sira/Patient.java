package com.sira.sira;

/**
 * Created by Jan on 29/03/2018.
 */

public class Patient {

    private int patId;
    private String firstName;
    private String secondName;
    private char gender;
    private String birthdate;
    private String ahvId;
    private String birthName;
    private String birthPlace;
    private String birthCountry;

    public Patient(int patId, String firstName, String secondName, char gender, String birthdate, String ahvId, String birthName, String birthPlace, String birthCountry) {
        this.patId = patId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.ahvId = ahvId;
        this.birthName = birthName;
        this.birthPlace = birthPlace;
        this.birthCountry = birthCountry;
    }


    public int getPatId() {
        return patId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public char getGender() {
        return gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAhvId() {
        return ahvId;
    }

    public String getBirthName() {
        return birthName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getBirthCountry() {
        return birthCountry;
    }
}
