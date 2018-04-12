package com.sira.sira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Jan on 03/04/2018.
 */

public class HpActivity extends Fragment implements View.OnClickListener{

    private int layoutId;
    private int hpLayoutId = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle bundle = getArguments();
        if(bundle != null){
            layoutId = bundle.getInt("layoutId");
        } else {
            layoutId = 0;
        }

        View myView=inflater.inflate(layoutId,container,false);

        TypedArray layouts = getResources().obtainTypedArray(R.array.hiplayouts);
        if (layoutId == layouts.getResourceId(hpLayoutId, 2)){
            RadioButton radioButton = (RadioButton) myView.findViewById(R.id.hp_rb_diag_andDiag);
            radioButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("hello", "hello");
                    createDialogBox(getResources().getString(R.string.eingabeAndereDiag));
                }
            });
        }

        return myView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View myView){
    }

    public void createDialogBox(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.speichern, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                input.getText().toString();
            }
        })
                .setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
}
