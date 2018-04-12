package com.sira.sira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by lea on 27.03.18.
 */

public class BodyDetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodypart_detail);

        Button primaryProthesis = (Button)findViewById(R.id.button_primaryProthesis);

        primaryProthesis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intBefore = getIntent();
                Bundle bundle = intBefore.getExtras();
                Intent implantType = new Intent(BodyDetailActivity.this, BasicLayoutActivity.class);
                bundle.putString("implantType", "primary");
                implantType.putExtras(bundle);
                startActivity(implantType);
                finish();
            }
        });
    }
}