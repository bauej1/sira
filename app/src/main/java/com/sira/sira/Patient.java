package com.sira.sira;

import android.databinding.BaseObservable;

/**
 * Created by Jan on 29/03/2018.
 */

public class Patient extends BaseObservable{

    private int patId;
    private String firstname;
    private String secondname;
    private String gender;
    private String dob;
    private String ssn;
    private String familynameatbirth;
    private String cityofbirth;
    private String countryofbirth;
    private int heightInCm;
    private int weightInKg;
    private boolean isFemale = false;
    private HPrimaryImplantData hprimImplantData;
    //private int asa;
    private String surgeryDate;
    private String firstSurgeon;
    private String secondSurgeon;
    private String language = "de";
    private int createdBy = 1;

    public Patient(int patId, String firstName, String secondName, String gender, String birthdate, String ahvId, String birthName, String birthPlace, String birthCountry, int heightInCm, int weightInKg, int asa, String surgeryDate, String firstSurgeon, String secondSurgeon) {

        this.hprimImplantData = new HPrimaryImplantData();

        this.patId = patId;
        this.firstname = firstName;
        this.secondname = secondName;
        this.gender = gender;
        this.dob = birthdate;
        this.ssn = ahvId;
        this.familynameatbirth = birthName;
        this.cityofbirth = birthPlace;
        this.countryofbirth = birthCountry;
//        this.heightInCm = heightInCm;
        this.hprimImplantData.setHEIGHT(heightInCm);
//        this.weightInKg = weightInKg;
        this.hprimImplantData.setWEIGHT(weightInKg);
        this.hprimImplantData.setMORBIDITY_STATE(asa);
        //this.asa = asa;
        this.surgeryDate = surgeryDate;
        this.firstSurgeon = firstSurgeon;
        this.secondSurgeon = secondSurgeon;

        if(gender.equals("f")){
            isFemale = true;
        }
    }

    public Patient(){

    }

    public int getPatId() {
        return patId;
    }

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getSecondName() {
        return secondname;
    }

    public void setSecondName(String secondName) {
        this.secondname = secondName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return dob;
    }

    public void setBirthdate(String birthdate) {
        this.dob = birthdate;
    }

    public String getAhvId() {
        return ssn;
    }

    public void setAhvId(String ahvId) {
        this.ssn = ahvId;
    }

    public String getBirthName() {
        return familynameatbirth;
    }

    public void setBirthName(String birthName) {
        this.familynameatbirth = birthName;
    }

    public String getCityofbirth() {
        return cityofbirth;
    }

    public void setCityofbirth(String cityofbirth) {
        this.cityofbirth = cityofbirth;
    }

    public String getBirthCountry() {
        return countryofbirth;
    }

    public void setBirthCountry(String birthCountry) {
        this.countryofbirth = birthCountry;
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

    public HPrimaryImplantData getHPrimaryImplantData(){
        return hprimImplantData;
    }

    public String getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(String surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public String getFirstSurgeon() {
        return firstSurgeon;
    }

    public void setFirstSurgeon(String firstSurgeon) {
        this.firstSurgeon = firstSurgeon;
    }

    public String getSecondSurgeon() {
        return secondSurgeon;
    }

    public void setSecondSurgeon(String secondSurgeon) {
        this.secondSurgeon = secondSurgeon;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
