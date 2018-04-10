package com.sira.sira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by lea on 27.03.18.
 */

public class BodyMainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodypart_main);

        ImageButton hip_button = (ImageButton)findViewById(R.id.hip_button);

        hip_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent hipIntent = new Intent(BodyMainActivity.this, BodySideActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bodypart", "h");
                hipIntent.putExtras(bundle);
                startActivity(hipIntent);
                finish();
            }
        });

        ImageButton knee_button = (ImageButton)findViewById(R.id.knee_button);

        knee_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent hipIntent = new Intent(BodyMainActivity.this, BodySideActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bodypart", "k");
                hipIntent.putExtras(bundle);
                startActivity(hipIntent);
                finish();
            }
        });
    }
}
