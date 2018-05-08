package com.sira.sira;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.android.databinding.library.baseAdapters.*;

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
    private int asa;
//    private Surgeon surgeon;
//    private HPrimaryImplantData hprimImplantData;

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
    }

    @Bindable
    public int getPatId() {
        return patId;
    }

    public void setPatId(int patId) {
        this.patId = patId;
        notifyPropertyChanged(BR.patId);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
        notifyPropertyChanged(BR.secondName);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
        notifyPropertyChanged(BR.birthdate);
    }

    @Bindable
    public String getAhvId() {
        return ahvId;
    }

    public void setAhvId(String ahvId) {
        this.ahvId = ahvId;
        notifyPropertyChanged(BR.ahvId);
    }

    @Bindable
    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
        notifyPropertyChanged(BR.birthName);
    }

    @Bindable
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
        notifyPropertyChanged(BR.birthPlace);
    }

    @Bindable
    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
        notifyPropertyChanged(BR.birthCountry);
    }

    @Bindable
    public int getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(int heightInCm) {
        this.heightInCm = heightInCm;
        notifyPropertyChanged(BR.heightInCm);
    }

    @Bindable
    public int getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(int weightInKg) {
        this.weightInKg = weightInKg;
        notifyPropertyChanged(BR.weightInKg);
    }

    @Bindable
    public boolean isHeightWeightUnknown() {
        return heightWeightUnknown;
    }

    public void setHeightWeightUnknown(boolean heightWeightUnknown) {
        this.heightWeightUnknown = heightWeightUnknown;
        notifyPropertyChanged(BR.heightWeightUnknown);
    }

    @Bindable
    public int getAsa(){ return asa; }

    public void setAsa(int asa){
        this.asa = asa;
        notifyPropertyChanged(BR.asa);
    }
}
