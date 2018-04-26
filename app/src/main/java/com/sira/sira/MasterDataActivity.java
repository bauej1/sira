package com.sira.sira;

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
    private EditText date;
    private Patient patient;
    private View myView;

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
                    getPatientData(patId.getText().toString());
                }
                return false;
            }
        });

        //final EditText editTextPatientNumber = (EditText) myView.findViewById(R.id.patId);
        //int patientNumber = Integer.parseInt(editTextPatientNumber.getText().toString());


        return myView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

     /*date = myView.findViewById(R.id.birthdate);
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                picker = new DatePickerDialog(BasicLayoutActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//
//                picker.show();
//            }
//        });*/

    private void getPatientData(String editPatId){
        if(editPatId == null){
            return;
        }

        CSVReader reader = getDummyDataFile();
        String[] nextValue;

        try {
            while((nextValue = reader.readNext()) != null){
                if(nextValue[0].equals(editPatId)){
                    patient = new Patient(Integer.parseInt(editPatId), nextValue[1], nextValue[2], nextValue[3].charAt(0), nextValue[4], nextValue[5], nextValue[6], nextValue[7], nextValue[8], Integer.parseInt(nextValue[9]), Integer.parseInt(nextValue[10]), Boolean.parseBoolean(nextValue[11]));
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
        TextView firstName = myView.findViewById(R.id.vorname);
        firstName.setText(patient.getFirstName());

        TextView secondName = myView.findViewById(R.id.nachname);
        secondName.setText(patient.getSecondName());

        TextView ahv = myView.findViewById(R.id.ahv);
        ahv.setText(patient.getAhvId());

        TextView birthPlace = myView.findViewById(R.id.geburtsort);
        birthPlace.setText(patient.getBirthPlace());

        TextView birthName = myView.findViewById(R.id.geburtsname);
        birthName.setText(patient.getBirthName());

        TextView birthCountry = myView.findViewById(R.id.geburtsland);
        birthCountry.setText(patient.getBirthCountry());

        RadioButton male = myView.findViewById(R.id.male);
        RadioButton female = myView.findViewById(R.id.female);

        if(patient.getGender() == 'm'){
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
    }
}
