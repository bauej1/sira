package com.sira.sira;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shuhart.stepview.StepView;

/**
 * Created by Jan on 03/04/2018.
 */

public class HpActivity extends AppCompatActivity {

    private StepView stepview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hp_aufnahme1);

        //get the actual step counter from the bundle extras and move to specific step
        //maybe set animate to false?
        //JABA
        stepview = findViewById(R.id.step_view);
        Bundle stepViewInfo = getIntent().getExtras();
        stepview.go(stepViewInfo.getInt("currentStep")+1, true);
    }
}
