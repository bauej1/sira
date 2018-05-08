package com.sira.sira;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


/**
 * Created by Jan on 03/04/2018.
 */

public class HpActivity extends Fragment implements View.OnClickListener {

    /**
     * All the ID Constants are used for static referencing to the layouts from the R.array.hiplayouts.
     */
    private static final int masterdataID = 2131427383;
    private static final int barcodeID = 2131427355;
    private static final int aufnahme1ID = 2131427374;
    private static final int aufnahme2ID = 2131427375;
    private static final int op1ID = 2131427376;
    private static final int op2ID = 2131427377;
    private static final int op3ID = 2131427378;
    /**
     * ===============================================================================================
     */
    private int layoutId;
    private int masterdata = 0;
    private int barcodeLayout = 1;
    private int hpLayoutAufnahme2 = 3;
    private int hpLayoutOperation1 = 4;
    private int hpLayoutOperation2 = 5;
    private int hpLayoutOperation3 = 6;
    private View myView;
    private SharedPreferences sp;
    private Patient p;
    private boolean patientLoaded = false;
    private TypedArray layouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loadData();
        layouts = getResources().obtainTypedArray(R.array.hiplayouts);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            layoutId = bundle.getInt("layoutId");
        } else {
            layoutId = 0;
        }

        myView = inflater.inflate(layoutId, container, false);
        fillLayoutData(layoutId);

        if (layoutId == layouts.getResourceId(barcodeLayout, 1)) {
            ImageButton barcodeButton = (ImageButton) myView.findViewById(R.id.scan_button);
            final IntentIntegrator integrator = new IntentIntegrator(getActivity());
            barcodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                    integrator.setPrompt("Scan a barcode");
                    integrator.setCameraId(0);  // Use a specific camera of the device
                    integrator.setBeepEnabled(false);
                    integrator.initiateScan();
                }

            });
            return myView;
        }

        if (layoutId == layouts.getResourceId(hpLayoutAufnahme2, 3)) {
            final RadioButton radioButtonDiagnosis = (RadioButton) myView.findViewById(R.id.hp_rb_diag_andDiag);
            radioButtonDiagnosis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndereDiag), radioButtonDiagnosis);
                }
            });

            final CheckBox checkBoxSurgery = (CheckBox) myView.findViewById(R.id.hp_cb_vorhOP_andOP);
            checkBoxSurgery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxCB(getResources().getString(R.string.eingabeAndereOP), checkBoxSurgery);
                }
            });
        }

        if (layoutId == layouts.getResourceId(hpLayoutOperation1, 4)) {
            final RadioButton radioButtonIntervention = (RadioButton) myView.findViewById(R.id.hp_rb_eingr_andEingr);
            radioButtonIntervention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndererEingriff), radioButtonIntervention);
                }
            });

            final RadioButton radioButtonAccess = (RadioButton) myView.findViewById(R.id.hp_rb_zugang_andZugang);
            radioButtonAccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndererZugang), radioButtonAccess);
                }
            });

            final RadioButton radioButtonPosition = (RadioButton) myView.findViewById(R.id.hp_rb_lagerung_andLagerung);
            radioButtonPosition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndereLagerung), radioButtonPosition);
                }
            });
        }

        if (layoutId == layouts.getResourceId(hpLayoutOperation2, 5)) {
            final CheckBox checkBoxSupplIntervention = (CheckBox) myView.findViewById(R.id.hp_cb_zusatzeingr_andere);
            checkBoxSupplIntervention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createDialogBoxCB(getResources().getString(R.string.eingabeAndererZusatzeingriff), checkBoxSupplIntervention);
                }
            });

        }

        if (layoutId == layouts.getResourceId(hpLayoutOperation3, 6)) {
            final ImageButton buttonInformation = (ImageButton) myView.findViewById(R.id.hp_ib_cement_info);
            final LinearLayout layoutInformation = (LinearLayout) myView.findViewById(R.id.hp_layout_infoCement);

            buttonInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layoutInformation.getVisibility() == View.INVISIBLE) {
                        layoutInformation.setVisibility(View.VISIBLE);
                    } else {
                        layoutInformation.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        return myView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View myView) {
    }

    public void createDialogBoxRB(String title, RadioButton radioButton1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        final RadioButton radioButton = radioButton1;

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.speichern, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (input.getText().toString().trim().length() > 0) {
                    radioButton.setText(input.getText().toString());
                } else {
                    radioButton.setChecked(false);
                    return;
                }
            }
        })
                .setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        radioButton.setChecked(false);
                        return;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#838182"));
    }


    public void createDialogBoxCB(String title, final CheckBox checkBox1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        final CheckBox checkBox = checkBox1;

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.speichern, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (input.getText().toString().trim().length() > 0) {
                    checkBox.setText(input.getText().toString());
                } else {
                    checkBox.toggle();
                    return;
                }
            }
        })
                .setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkBox.toggle();
                        return;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#838182"));
    }

    /**
     * Load data from SharedPreferences called patientData from System and reconvert it from Json to a Patient Object.
     */
    private void loadData(){
        sp = getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("patient", "");

        if(sp.getBoolean("loaded", false) == true) {
            patientLoaded = true;
        }
        p = gson.fromJson(json, Patient.class);
    }

    /**
     * Calls the methods to fill the different layouts by layoutId.
     *
     * @param layoutId - the id of the layout in integer
     */
    private void fillLayoutData(int layoutId){

        if(patientLoaded){
            switch(layoutId){
                case masterdataID:
                    fillMasterDataFields();
                    break;
                case aufnahme1ID:
                    fillAufnahmeData1();
                    break;
            }
        }
    }

    /**
     * This method fills data from a patient object in the layout for Aufnahme1.
     */
    private void fillAufnahmeData1(){
        EditText height = myView.findViewById(R.id.hp_et_height);
        EditText weight = myView.findViewById(R.id.hp_et_weight);

        height.setText(p.getHeightInCm() + "");
        weight.setText(p.getWeightInKg() + "");
    }

    /**
     * This method fills master data from a patient object in the layout for masterdata.
     */
    private void fillMasterDataFields(){
        EditText pid = myView.findViewById(R.id.patId);
        EditText firstName = myView.findViewById(R.id.vorname);
        EditText secondName = myView.findViewById(R.id.nachname);
        RadioButton male = myView.findViewById(R.id.male);
        RadioButton female = myView.findViewById(R.id.female);
        EditText birthdate = myView.findViewById(R.id.birthdate);
        EditText ahv = myView.findViewById(R.id.ahv);
        EditText birthPlace = myView.findViewById(R.id.geburtsort);
        EditText birthName = myView.findViewById(R.id.geburtsname);
        EditText birthCountry = myView.findViewById(R.id.geburtsland);
        EditText height = myView.findViewById(R.id.groesse);
        EditText weight = myView.findViewById(R.id.gewicht);
        EditText asa = myView.findViewById(R.id.asa);
        EditText surgeryDate = myView.findViewById(R.id.surgDate);
        EditText firstSurgeon = myView.findViewById(R.id.firstSurgeon);
        EditText secondSurgeon = myView.findViewById(R.id.secSurgeon);

        pid.setText(p.getPatId() + "");
        firstName.setText(p.getFirstName());
        secondName.setText(p.getSecondName() + " (" + p.getBirthName() + ")");
        if(p.getGender().equals('m')){
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        birthdate.setText(p.getBirthdate());
        ahv.setText(p.getAhvId());
        birthPlace.setText(p.getBirthPlace());
        birthCountry.setText(p.getBirthCountry());
        weight.setText(p.getWeightInKg() + " kg");
        height.setText(p.getHeightInCm() + " cm");
        asa.setText(p.getAsa() + "");
    }
}
