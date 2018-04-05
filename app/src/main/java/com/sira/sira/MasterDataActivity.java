package com.sira.sira;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.opencsv.CSVReader;
import com.shuhart.stepview.StepView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Jan on 28/03/2018.
 */
public class MasterDataActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private int currentStep = 0;
    private GestureDetector gDetector;
    private StepView stepView;
    private Patient patient;
    private String bodyPart;
    private String bodySide;
    private String implantType;
    private EditText date;
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterdata);

        getIntentBundle(getIntent());

        gDetector = new GestureDetector(this);
        stepView = findViewById(R.id.step_view);

        final EditText patId = (EditText) findViewById(R.id.patId);
        patId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    getPatientData(patId.getText().toString());
                }
                return false;
            }
        });

        date = findViewById(R.id.birthdate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(MasterDataActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);

                picker.show();
            }
        });
    }

    /**
     * This method gets all the bundle extras from the intent
     *
     * @param intent - the intent from which this actvitiy is called
     */
    private void getIntentBundle(Intent intent){
        Bundle formInfo = intent.getExtras();
        bodySide = formInfo.getString("bodyside");
        bodyPart = formInfo.getString("bodypart");
        implantType = formInfo.getString("implantType");
    }

    /**
     * This method fills all the TextView elements with its patient data.
     */
    private void fillFieldsWithPatData(){
        TextView firstName = (TextView) findViewById(R.id.vorname);
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
        }
    }

    /**
     * Gets the patient data and fills the Patient object with it's data.
     *
     * @param editPatId - the entered Patient ID
     */
    private void getPatientData(String editPatId){
        if(editPatId == null){
            return;
        }

        CSVReader reader = getDummyDataFile();
        String[] nextValue;

        try {
            while((nextValue = reader.readNext()) != null){
                if(nextValue[0].equals(editPatId)){
                    patient = new Patient(Integer.parseInt(editPatId), nextValue[1], nextValue[2], nextValue[3].charAt(0), nextValue[4], nextValue[5], nextValue[6], nextValue[7], nextValue[8]);
                    fillFieldsWithPatData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets the dummy data file dummyPatients.csv from the android file system.
     *
     * @return CSVReader
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

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent firstMotion, MotionEvent secondMotion, float v, float v1) {
        float firstX = firstMotion.getX();
        float secondX = secondMotion.getX();

        if(firstX < secondX){
            swipeLeft();
        } else {
            swipeRight();
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gDetector.onTouchEvent(me);
    }

    public void swipeRight(){
        if (currentStep < stepView.getStepCount() - 1) {
            currentStep++;
            stepView.go(currentStep, true);
            if(bodyPart.equals("h")){
                /**
                 * This construct needs to be moved in a public method file.. That's not a really beautiful solution.
                 * It's needed again and again..
                 * JABA
                 */
                Intent intent = new Intent(MasterDataActivity.this, HpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("currentStep", currentStep);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            stepView.done(true);
        }
    }

    public void swipeLeft(){
        if (currentStep > 0) {
            currentStep--;
        }
        stepView.done(false);
        stepView.go(currentStep, true);
    }
}

