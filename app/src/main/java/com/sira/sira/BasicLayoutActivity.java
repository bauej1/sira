package com.sira.sira;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.*;
import com.google.zxing.integration.android.IntentIntegrator;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

/**
 * Created by Jan on 28/03/2018.
 */
public class BasicLayoutActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private int currentStep = 0;
    private GestureDetector gDetector;
    private StepView stepView;
    private Patient patient;
    private String bodyPart;
    private String bodySide;
    private String implantType;
    private EditText date;
    private DatePickerDialog picker;
    private TypedArray hipLayouts;
    private int layoutId;
    String scannedContentFull;
    String scannedContentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout);

        if(savedInstanceState !=  null){
            return;
        }

        hipLayouts = getResources().obtainTypedArray(R.array.hiplayouts);

        MasterDataActivity mda = new MasterDataActivity();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, mda).commit();

        getIntentBundle(getIntent());

        gDetector = new GestureDetector(this);
        stepView = findViewById(R.id.step_view);
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

        if((firstX + 200) < secondX){
            swipeLeft();
        } else {
            swipeRight();
        }
        layoutId = hipLayouts.getResourceId(currentStep, 0);
        Bundle bundle = new Bundle();
        bundle.putInt("layoutId", layoutId);
        HpActivity hp = new HpActivity();
        hp.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLayout, hp, "hpfragment");
        ft.commit();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        com.google.zxing.integration.android.IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        TextView scanContent = (TextView) findViewById(R.id.scan_content);
        ArrayList<String> scanArray = new ArrayList<>();
        Log.d("got into activity", "got into activity");

        if (result != null) {
            Log.d("log first if", "log first if");
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                //String scannedContent = result.getContents();
                scannedContentFull = result.toString();
                scannedContentNumber = result.getContents();
                scanArray.add(scannedContentFull);
                //scanContent.setText("Gescannter Barcode: "+ scannedContentFull);
                for (String el : scanArray) {
                    scanContent.setText(scanContent.getText() + System.lineSeparator() + el);
                }
                dialogSaveScan();
            }
        } else {
            Log.d("log second if", "log second if");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void dialogSaveScan() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        String message = getResources().getString(R.string.dialog_barcodeScan);
        builder.setMessage(message + "\n\n" + scannedContentNumber);
        builder.setPositiveButton(R.string.speichern, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();

        Button negativeButton = dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#838182"));
    }
}

