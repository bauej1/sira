package com.sira.sira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;

/**
 * Created by Jan on 03/04/2018.
 */

public class HpActivity extends Fragment implements View.OnClickListener{

    private int layoutId;
    private int hpLayoutAufnahme2 = 2;
    private int hpLayoutOperation1 = 3;

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
        if (layoutId == layouts.getResourceId(hpLayoutAufnahme2, 2)){
            final RadioButton radioButtonDiagnosis = (RadioButton) myView.findViewById(R.id.hp_rb_diag_andDiag);
            radioButtonDiagnosis.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndereDiag), radioButtonDiagnosis);
                }
            });

            final CheckBox checkBoxSurgery = (CheckBox) myView.findViewById(R.id.hp_cb_vorhOP_andOP);
            checkBoxSurgery.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   createDialogBoxCB(getResources().getString(R.string.eingabeAndereOP), checkBoxSurgery);
                }
            });
        }

        if (layoutId == layouts.getResourceId(hpLayoutOperation1, 3)){
            final RadioButton radioButtonIntervention = (RadioButton) myView.findViewById(R.id.hp_rb_eingr_andEingr);
            radioButtonIntervention.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndererEingriff), radioButtonIntervention);
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

    public void createDialogBoxRB(String title, RadioButton radioButton1){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        final RadioButton radioButton = radioButton1;

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.speichern, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                radioButton.setText(input.getText().toString());
            }
        })
                .setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#838182"));
    }


    public void createDialogBoxCB(String title, final CheckBox checkBox1){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        final CheckBox checkBox = checkBox1;

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.speichern, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkBox1.setText(input.getText().toString());
            }
        })
                .setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#838182"));
    }
}