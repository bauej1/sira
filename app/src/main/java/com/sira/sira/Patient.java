package com.sira.sira;

import android.databinding.BaseObservable;

/**
 * Created by Jan on 29/03/2018.
 */

public class Patient extends BaseObservable{

    private int patId;
    private String firstName;
    private String secondName;
    private String gender;
    private String birthdate;
    private String ahvId;
    private String birthName;
    private String birthPlace;
    private String birthCountry;
    private int heightInCm;
    private int weightInKg;
    private boolean heightWeightUnknown = false;
    private boolean isFemale = false;
    private HPrimaryImplantData hprimImplantData;
    private int asa;

    public Patient(int patId, String firstName, String secondName, String gender, String birthdate, String ahvId, String birthName, String birthPlace, String birthCountry, int heightInCm, int weightInKg, boolean heightWeightUnknown, int asa) {
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
        this.asa = asa;

        if(gender.equals("f")){
            isFemale = true;
        }

        this.hprimImplantData = new HPrimaryImplantData();
    }

    public int getPatId() {
        return patId;
    }

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAhvId() {
        return ahvId;
    }

    public void setAhvId(String ahvId) {
        this.ahvId = ahvId;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }


    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public int getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(int heightInCm) {
        this.heightInCm = heightInCm;

    }

    public int getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(int weightInKg) {
        this.weightInKg = weightInKg;
    }

    public boolean isHeightWeightUnknown() {
        return heightWeightUnknown;
    }

    public void setHeightWeightUnknown(boolean heightWeightUnknown) {
        this.heightWeightUnknown = heightWeightUnknown;
    }

    public int getAsa(){ return asa; }

    public void setAsa(int asa){
        this.asa = asa;
    }

    public HPrimaryImplantData getHPrimaryImplantData(){
        return hprimImplantData;
    }
}
