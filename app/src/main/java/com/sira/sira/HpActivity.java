package com.sira.sira;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jan on 03/04/2018.
 */

public class HpActivity extends Fragment implements View.OnClickListener{

    private int layoutId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(savedInstanceState!=null){
        }

        Bundle bundle = getArguments();
        if(bundle != null){
            layoutId = bundle.getInt("layoutId");
        } else {
            layoutId = 0;
        }

        View myView=inflater.inflate(layoutId,container,false);
        return myView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View myView){
        if(myView.getId()==R.id.patId){

        }
    }
}
