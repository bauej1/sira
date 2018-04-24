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
    private int heightInCm;
    private int weightInKg;
    private boolean heightWeightUnknown = false;

    public Patient(int patId, String firstName, String secondName, char gender, String birthdate, String ahvId, String birthName, String birthPlace, String birthCountry, int heightInCm, int weightInKg, boolean heightWeightUnknown) {
        this.patId = patId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.ahvId = ahvId;
        this.birthName = birthName;
        this.birthPlace = birthPlace;
        this.birthCountry = birthCountry;
        this.heightInCm = heightInCm;
        this.weightInKg = weightInKg;
        this.heightWeightUnknown = heightWeightUnknown;
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

    public int getHeightInCm() {
        return heightInCm;
    }

    public int getWeightInKg() {
        return weightInKg;
    }
}
