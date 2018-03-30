package com.sira.sira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by lea on 30.03.18.
 */

public class BodySideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodypart_side);

        Button left_button = (Button)findViewById(R.id.left_button);

        left_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(BodySideActivity.this, BodyDetailActivity.class));
            }
        });

        Button right_button = (Button)findViewById(R.id.right_button);

        right_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(BodySideActivity.this, BodyDetailActivity.class));
            }
        });
    }
}
