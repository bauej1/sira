package com.sira.sira;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jan on 05/04/2018.
 */

public class MasterDataActivity extends Fragment{

    private EditText patId;
    private View myView;
    private Patient p;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean patientLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TypedArray hipLayouts = getResources().obtainTypedArray(R.array.hiplayouts);
        int layoutId = hipLayouts.getResourceId(0, 0);

        myView = inflater.inflate(layoutId, container, false);

        patId = myView.findViewById(R.id.patId);
        patId.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent){
                if (i == EditorInfo.IME_ACTION_DONE) {
                    isPatIdValid(patId.getText().toString());
                    p = getPatientData(patId.getText().toString());
                    patientLoaded = true;
                    loadPatientToSharedPref(p, patientLoaded);
                    fillMasterDataFields();
                }
                return false;
            }
        });

        return myView;
    }

    private boolean isPatIdValid(String enteredPatId){
        int id = Integer.parseInt(enteredPatId);
        if(id < 10000){ //Jan --> Maybe check some other constraints and mark the input field red or something like that..
            return false;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Gets the patient data and reads the received values to push it into a Patient Object.
     *
     * @param editPatId
     *
     * @return a Patient Object
     */
    private Patient getPatientData(String editPatId){
        if(editPatId == null){
            return null;
        }

        CSVReader reader = getDummyDataFile();
        String[] nextValue;

        try {
            while((nextValue = reader.readNext()) != null){
                if(nextValue[0].equals(editPatId)){
                    Patient p = new Patient(Integer.parseInt(editPatId), nextValue[1], nextValue[2], nextValue[3], nextValue[4], nextValue[5], nextValue[6], nextValue[7], nextValue[8], Integer.parseInt(nextValue[9]), Integer.parseInt(nextValue[10]), Integer.parseInt(nextValue[11]), nextValue[12], nextValue[13], nextValue[14]);
                    return p;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the dummy data file from a specific path inside the Samsung Galaxy Tab.
     *
     * @return a CSVReader Object
     */
    private CSVReader getDummyDataFile(){
        File file = new File(Environment.getExternalStorageDirectory() + "/Documents/dummyPatients.csv");

        try {
            return new CSVReader(new FileReader(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads the retrieved Patient Object into the SharedPreferences on the Android System.
     *
     * @param p - Patient
     */
    private void loadPatientToSharedPref(Patient p, boolean patientLoaded){
        sp = getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
        editor = sp.edit();

        Gson gson = new Gson();
        String patientJson = gson.toJson(p);

        editor.putString("patient", patientJson);
        editor.putBoolean("loaded", patientLoaded);
        editor.apply();
        editor.commit();
    }

    /**
     * This method fills master data from a patient object in the layout for masterdata.
     */
    private void fillMasterDataFields(){
        EditText pid = myView.findViewById(R.id.patId);
        EditText firstName = myView.findViewById(R.id.vorname);
        EditText secondName = myView.findViewById(R.id.nachname);
        RadioButton male = myView.findViewById(R.id.male);
        RadioButton female = myView.findViewById(R.id.female);
        EditText birthdate = myView.findViewById(R.id.birthdate);
        EditText ahv = myView.findViewById(R.id.ahv);
        EditText birthPlace = myView.findViewById(R.id.geburtsort);
        EditText birthCountry = myView.findViewById(R.id.geburtsland);
        EditText height = myView.findViewById(R.id.groesse);
        EditText weight = myView.findViewById(R.id.gewicht);
        EditText asa = myView.findViewById(R.id.asa);
        EditText surgeryDate = myView.findViewById(R.id.surgDate);
        EditText firstSurgeon = myView.findViewById(R.id.firstSurgeon);
        EditText secondSurgeon = myView.findViewById(R.id.secSurgeon);

        pid.setText(p.getPatId() + "");
        firstName.setText(p.getFirstName());
        secondName.setText(p.getSecondName() + " (" + p.getBirthName() + ")");
        if(p.getGender().equals('m')){
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        birthdate.setText(p.getBirthdate());
        ahv.setText(p.getAhvId());
        birthPlace.setText(p.getCityofbirth());
        birthCountry.setText(p.getBirthCountry());
        weight.setText(p.getHPrimaryImplantData().getWEIGHT() + " kg");
        height.setText(p.getHPrimaryImplantData().getHEIGHT() + " cm");
        asa.setText("ASA: " + p.getHPrimaryImplantData().getMORBIDITY_STATE());
        surgeryDate.setText(p.getSurgeryDate());
        firstSurgeon.setText(p.getFirstSurgeon());
        secondSurgeon.setText(p.getSecondSurgeon());
    }
}
