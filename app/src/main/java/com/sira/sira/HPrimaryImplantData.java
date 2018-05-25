package com.sira.sira;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jan on 24/04/2018.
 * This class is used as a container to collect all the formular data before transforming into XML Format to push it into the database.
 */

public class HPrimaryImplantData {

    private String charnleyClass;
    private String diagnosis;
    private ArrayList<String> previousSurgeries = new ArrayList<>();
    private String asaState;
    private String intervention;
    private String accessDirection;
    private String position;
    private String fixation;
    private ArrayList<String> additionalInterventions = new ArrayList<>();
    private int cementingType;

    public HPrimaryImplantData(String charnleyClass, String diagnosis, ArrayList<String> previousSurgeries, String asaState, String intervention, String accessDirection, String position, String fixation, ArrayList<String> additionalInterventions, int cementingType) {
        this.charnleyClass = charnleyClass;
        this.diagnosis = diagnosis;
        this.previousSurgeries = previousSurgeries;
        this.asaState = asaState;
        this.intervention = intervention;
        this.accessDirection = accessDirection;
        this.position = position;
        this.fixation = fixation;
        this.additionalInterventions = additionalInterventions;
        this.cementingType = cementingType;
    }

    public HPrimaryImplantData(){

    }

    public String getCharnleyClass() {
        return charnleyClass;
    }

    public void setCharnleyClass(String charnleyClass) {
        this.charnleyClass = charnleyClass;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPreviousSurgeries(int i) {
        if(previousSurgeries.size() > 0){
            return previousSurgeries.get(i);
        }
        return null;
    }

    public int getPreviousSurgeriesSize(){
        return previousSurgeries.size();
    }

    public void setPreviousSurgeries(String previousSurgeries) {
        this.previousSurgeries.add(previousSurgeries);
    }

    public String getAsaState() {
        return asaState;
    }

    public void setAsaState(String asaState) {
        this.asaState = asaState;
    }

    public String getIntervention() {
        return intervention;
    }

    public void setIntervention(String intervention) {
        this.intervention = intervention;
    }

    public String getAccessDirection() {
        return accessDirection;
    }

    public void setAccessDirection(String accessDirection) {
        this.accessDirection = accessDirection;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFixation() {
        return fixation;
    }

    public void setFixation(String fixation) {
        this.fixation = fixation;
    }

    public String getAdditionalInterventions(int i) {
        if(additionalInterventions.size() > 0){
            return additionalInterventions.get(i);
        }
        return null;
    }

    public void setAdditionalInterventions(String additionalIntervention) {
        this.additionalInterventions.add(additionalIntervention);
    }

    public int getAdditionalInterventionSize(){
        return additionalInterventions.size();
    }

    public int getCementingType() {
        return cementingType;
    }

    public void setCementingType(int cementingType) {
        this.cementingType = cementingType;
    }
}
