package com.sira.sira;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;

import com.shuhart.stepview.StepView;

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

        if(firstX < secondX){
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
        ft.replace(R.id.fragmentLayout, hp);
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
}

