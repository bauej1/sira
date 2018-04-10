package com.sira.sira;

import android.app.DatePickerDialog;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Jan on 05/04/2018.
 */

public class MasterDataActivity extends Fragment implements View.OnClickListener {

    private EditText patId;
    private EditText date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        View myView = inflater.inflate(R.layout.masterdata, container, false);
        return myView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View myView) {
        if (myView.getId() == R.id.patId) {
            patId = (EditText) myView.findViewById(R.id.patId);
            patId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        getPatientData(patId.getText().toString());
                    }
                    return false;
                }
            });
        }
        /*date = myView.findViewById(R.id.birthdate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(BasicLayoutActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);

                picker.show();
            }
        });*/
    }

    private void getPatientData(String editPatId){
        if(editPatId == null){
            return;
        }

        CSVReader reader = getDummyDataFile();
        String[] nextValue;

        try {
            while((nextValue = reader.readNext()) != null){
                if(nextValue[0].equals(editPatId)){
                    //patient = new Patient(Integer.parseInt(editPatId), nextValue[1], nextValue[2], nextValue[3].charAt(0), nextValue[4], nextValue[5], nextValue[6], nextValue[7], nextValue[8]);
                    fillFieldsWithPatData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CSVReader getDummyDataFile(){
        File file = new File(Environment.getExternalStorageDirectory() + "/Documents/dummyPatients.csv");
        try {
            return new CSVReader(new FileReader(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void fillFieldsWithPatData(){
        /*TextView firstName = (TextView) findViewById(R.id.vorname);
        firstName.setText(patient.getFirstName());

        TextView secondName = (TextView) findViewById(R.id.nachname);
        secondName.setText(patient.getSecondName());

        TextView ahv = (TextView) findViewById(R.id.ahv);
        ahv.setText(patient.getAhvId());

        TextView birthPlace = (TextView) findViewById(R.id.geburtsort);
        birthPlace.setText(patient.getBirthPlace());

        TextView birthName = (TextView) findViewById(R.id.geburtsname);
        birthName.setText(patient.getBirthName());

        TextView birthCountry = (TextView) findViewById(R.id.geburtsland);
        birthCountry.setText(patient.getBirthCountry());

        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);

        if(patient.getGender() == 'm'){
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }*/
    }


}
