package com.sira.sira;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by Jan on 03/04/2018.
 */

public class HpActivity extends Fragment implements View.OnClickListener {

    /**
     * All the ID Constants are used for static referencing to the layouts from the R.array.hiplayouts.
     */
    private static final int masterdataID = 2131427381;     //HINT! THESE NUMBERS ARE MEMORY LOCATIONS IN THE TABLET.
    private static final int aufnahme2ID = 2131427374;      //      THOSE LOCATIONS CAN CHANGE WHEN THE APPLICATION IS DOWNLOADED TO ANOTHER
    private static final int finishedID = 2131427375;       //      TABLET. THERE IS A VARIABLE WHICH IS LOGGED CALLED "layoutId".
    private static final int op1ID = 2131427376;            //      CHECK THIS VARIABLE IN LOGCAT TO ADAPT THESE NUMBERS
    private static final int op2ID = 2131427377;
    /**
     * ===============================================================================================
     */
    private int layoutId;
    private int masterdata = 0;
    private int barcodeLayout = 1;
    private int hpLayoutAufnahme2 = 2;
    private int hpLayoutOperation1 = 3;
    private int hpLayoutOperation2 = 4;
    private int hpLayoutFinished = 5;

    private View myView;
    private SharedPreferences sp;
    private Patient p;
    private boolean patientLoaded = false;
    private TypedArray layouts;
    private SharedPreferences.Editor editor;

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

        Log.d("layoutId", layoutId+"");

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

        if (layoutId == layouts.getResourceId(hpLayoutAufnahme2, 2)) {
            //Diagnosis =============================================================================================================
            final RadioGroup rgDiagnosis = (RadioGroup) myView.findViewById(R.id.hp_rg_diagnose);
            rgDiagnosis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    int index = rgDiagnosis.indexOfChild(myView.findViewById(rgDiagnosis.getCheckedRadioButtonId())) + 1;
                    p.getHPrimaryImplantData().setDiagnosis(index + "");
                    loadPatientToSharedPref(p, true);
                }
            });

            final RadioButton radioButtonDiagnosis = (RadioButton) myView.findViewById(R.id.hp_rb_diag_andDiag);
            radioButtonDiagnosis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndereDiag), radioButtonDiagnosis);
                }
            });

            //Previous Surgery ======================================================================================================
            CheckBox cb1 = myView.findViewById(R.id.hp_cb_vorhOP_keine);
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb2 = myView.findViewById(R.id.hp_cb_vorhOP_arthrodese);
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb3 = myView.findViewById(R.id.hp_cb_vorhOP_huftarthro);
            cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                }
            });

            CheckBox cb4 = myView.findViewById(R.id.hp_cb_vorhOP_ostFem);
            cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb5 = myView.findViewById(R.id.hp_cb_vorhOP_ostBeck);
            cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb6 = myView.findViewById(R.id.hp_cb_vorhOP_andOP);
            cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb7 = myView.findViewById(R.id.hp_cb_vorhOP_ostsAcet);
            cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb8 = myView.findViewById(R.id.hp_cb_vorhOP_ostsFem);
            cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setPreviousSurgeries(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            final CheckBox checkBoxSurgery = (CheckBox) myView.findViewById(R.id.hp_cb_vorhOP_andOP);
            checkBoxSurgery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxCB(getResources().getString(R.string.eingabeAndereOP), checkBoxSurgery);
                }
            });

            //Charnley Class =======================================================================================================
            final ImageButton buttonCharnleyInfo = (ImageButton) myView.findViewById(R.id.hp_ib_charnley_info);
            final LinearLayout layoutCharnelyInformation = (LinearLayout) myView.findViewById(R.id.hp_ll_infoCharnley);

            RadioGroup rgCharnley = (RadioGroup) myView.findViewById(R.id.hp_rb_charnley_group);
            rgCharnley.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    RadioButton checkedButton = (RadioButton) rg.findViewById(checkedId);
                    p.getHPrimaryImplantData().setCharnleyClass(checkedButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            buttonCharnleyInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (layoutCharnelyInformation.getVisibility() == View.INVISIBLE){
                        layoutCharnelyInformation.setVisibility(View.VISIBLE);
                    } else {
                        layoutCharnelyInformation.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        if (layoutId == layouts.getResourceId(hpLayoutOperation1, 3)) {
            //Eingriff  =======================================================================================================
            final RadioButton radioButtonIntervention = (RadioButton) myView.findViewById(R.id.hp_rb_eingr_andEingr);
            radioButtonIntervention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndererEingriff), radioButtonIntervention);
                }
            });

            RadioGroup rgEingriff = (RadioGroup) myView.findViewById(R.id.hp_rg_eingriff);
            rgEingriff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    RadioButton checkedButton = (RadioButton) rg.findViewById(checkedId);
                    p.getHPrimaryImplantData().setIntervention(checkedButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            //Zugang =========================================================================================================
            final RadioButton radioButtonAccess = (RadioButton) myView.findViewById(R.id.hp_rb_zugang_andZugang);
            radioButtonAccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndererZugang), radioButtonAccess);
                }
            });

            RadioGroup rgAccess = (RadioGroup) myView.findViewById(R.id.hp_rg_access);
            rgAccess.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    RadioButton checkedButton = (RadioButton) rg.findViewById(checkedId);
                    p.getHPrimaryImplantData().setAccessDirection(checkedButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            //Lagerung =======================================================================================================
            final RadioButton radioButtonPosition = (RadioButton) myView.findViewById(R.id.hp_rb_lagerung_andLagerung);
            radioButtonPosition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBoxRB(getResources().getString(R.string.eingabeAndereLagerung), radioButtonPosition);
                }
            });

            RadioGroup rgPosition = (RadioGroup) myView.findViewById(R.id.hp_rg_position);
            rgPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    RadioButton checkedButton = (RadioButton) rg.findViewById(checkedId);
                    p.getHPrimaryImplantData().setPosition(checkedButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });
        }

        if (layoutId == layouts.getResourceId(hpLayoutOperation2, 4)) {
            //SupplIntervention =======================================================================================================
            final CheckBox checkBoxSupplIntervention = (CheckBox) myView.findViewById(R.id.hp_cb_zusatzeingr_andere);
            checkBoxSupplIntervention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createDialogBoxCB(getResources().getString(R.string.eingabeAndererZusatzeingriff), checkBoxSupplIntervention);
                }
            });

            CheckBox cb1 = myView.findViewById(R.id.hp_cb_zusatzeingr_andere);
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setAdditionalInterventions(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb2 = myView.findViewById(R.id.hp_cb_zusatzeingr_keine);
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setAdditionalInterventions(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb3 = myView.findViewById(R.id.hp_cb_zusatzeingr_knöchPfannen);
            cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setAdditionalInterventions(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb4 = myView.findViewById(R.id.hp_cb_zusatzeingr_proxFemur);
            cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setAdditionalInterventions(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb5 = myView.findViewById(R.id.hp_cb_zusatzeingr_trochanteros);
            cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setAdditionalInterventions(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            CheckBox cb6 = myView.findViewById(R.id.hp_cb_zusatzeingr_zentrKnoch);
            cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    p.getHPrimaryImplantData().setAdditionalInterventions(compoundButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            //Fixation ==============================================================================================================
            RadioGroup rgFixation = (RadioGroup) myView.findViewById(R.id.hp_rb_fixation);
            rgFixation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    RadioButton checkedButton = (RadioButton) rg.findViewById(checkedId);
                    p.getHPrimaryImplantData().setFixation(checkedButton.getText().toString());
                    loadPatientToSharedPref(p, true);
                }
            });

            //Zementiertechnik =======================================================================================================

            RadioGroup rgZementtechnik = (RadioGroup) myView.findViewById(R.id.hp_rb_zementiertechnik);
            rgZementtechnik.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged(RadioGroup rg, int checkedId){
                    RadioButton checkedButton = (RadioButton) rg.findViewById(checkedId);
                    int zementTechnikId;
                    if(checkedButton.getText().toString().equals("erste Generation")){
                        zementTechnikId = 1;
                    } else if(checkedButton.getText().toString().equals("zweite Generation")){
                        zementTechnikId = 2;
                    } else {
                        zementTechnikId = 3;
                    }
                    p.getHPrimaryImplantData().setCementingType(zementTechnikId);
                    loadPatientToSharedPref(p, true);
                }
            });

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

        if (layoutId == layouts.getResourceId(hpLayoutFinished, 5)) {
            final ImageButton bUpload = (ImageButton) myView.findViewById(R.id.hp_uploadButton);
            final ProgressBar progressBar = (ProgressBar) myView.findViewById(R.id.hp_progressBar);
            final TextView infoUploadLabel = (TextView) myView.findViewById(R.id.hp_tv_upload);
            final ImageView responseImage = (ImageView) myView.findViewById(R.id.responseImage);
            final TextView responseText = (TextView) myView.findViewById(R.id.responseText);

            bUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //XmlBuilder xmlBuilder = XmlBuilder.getInstance();       // get instance from XmlBuilder
                    //xmlBuilder.loadXmlTemplate();                           // load the Xml Template for uploading the data
//                    try {
//                        xmlBuilder.loadAllDataIntoXml(p);                       // fill the Xml Template with data
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
                    progressBar.setVisibility(View.VISIBLE);
                    bUpload.setVisibility(View.INVISIBLE);
                    infoUploadLabel.setVisibility(View.INVISIBLE);

                    if(ServerHelpService.initRequestQueue()){
                        ServerHelpService.loadIntoServer(p, new VolleyCallback() {
                            @Override
                            public void onSuccessResponse(String result) {

                                if(hasXmlValidData(result)){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    responseImage.setVisibility(View.VISIBLE);
                                    responseImage.setImageResource(R.drawable.ic_check_circle_white_100dp);
                                    responseText.setText(R.string.uploadSuccessful);
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    responseImage.setVisibility(View.VISIBLE);
                                    responseImage.setImageResource(R.drawable.ic_highlight_off_white_100dp);
                                    responseText.setText(R.string.uploadNotSuccessful);
                                }
                            }
                        });
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        responseImage.setVisibility(View.VISIBLE);
                        responseImage.setImageResource(R.drawable.ic_highlight_off_white_100dp);
                        responseText.setText(R.string.uploadNotSuccessfulConnection);
                    }
                }
            });
        }
        return myView;
    }

    /**
     * This method checks if the data in the uploading xml file is valid or not.
     *
     * @param result - the result answer from the Server
     * @return true if the XML file is valid, false if not
     */
    public boolean hasXmlValidData(String result){
        if(result.contains("<haserrors>true</haserrors>")){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View myView) {
    }

    /**
     * This method creates a dialogBox if a specific radioButton is selected
     * @param title title of the dialogBox
     * @param radioButton1 the selected radioButton
     */
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

    /**
     * This method creates a dialogBox if a specific comboBox is selected
     * @param title title of the dialogBox
     * @param checkBox1 the selected checkBox
     */
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
                case aufnahme2ID:
                    fillAufnahmeData2();
                    break;
                case op1ID:
                    fillOperationData1();
                    break;
                case op2ID:
                    fillOperationData2();
                    break;
            }
        }
    }

    /**
     * This method fills data from a patient object in the layout for Aufnahme2.
     */
    private void fillAufnahmeData2(){
        RadioGroup rgCharnley = myView.findViewById(R.id.hp_rb_charnley_group);
        RadioGroup rgDiagnosis = myView.findViewById(R.id.hp_rg_diagnose);
        RadioGroup rgPrevOP = myView.findViewById(R.id.hp_cb_vorhOP);

        for(int i = 0; i < rgCharnley.getChildCount(); i++){
            RadioButton button = (RadioButton) rgCharnley.getChildAt(i);
            if(button.getText().toString().equals(p.getHPrimaryImplantData().getCharnleyClass())){
                    button.setChecked(true);
                }
            }

        for(int i = 0; i < rgDiagnosis.getChildCount(); i++){
            RadioButton button = (RadioButton) rgDiagnosis.getChildAt(i);
            if(button.getText().toString().equals(p.getHPrimaryImplantData().getDiagnosis())){
                button.setChecked(true);
            }
        }

        for(int i = 0; i < rgPrevOP.getChildCount(); i++){
            CheckBox box = (CheckBox) rgPrevOP.getChildAt(i);
            int storedSurgerySize = p.getHPrimaryImplantData().getPreviousSurgeriesSize();

            for(int j = 0; j < storedSurgerySize; j++){
                if(box.getText().toString().equals(p.getHPrimaryImplantData().getPreviousSurgeries(j))){
                    box.setChecked(true);
                }
            }
        }

    }

    /**
     * This method fills data from a patient object in the layout for Operation1.
     */
    private void fillOperationData1(){
        RadioGroup rgIntervention = myView.findViewById(R.id.hp_rg_eingriff);
        RadioGroup rgAccess = myView.findViewById(R.id.hp_rg_access);
        RadioGroup rgPosition = myView.findViewById(R.id.hp_rg_position);

        for (int i = 0; i < rgIntervention.getChildCount(); i++){
            RadioButton button = (RadioButton) rgIntervention.getChildAt(i);
            if(button.getText().toString().equals(p.getHPrimaryImplantData().getIntervention())){
                button.setChecked(true);
            }
        }

        for (int i = 0; i < rgAccess.getChildCount(); i++){
            RadioButton button = (RadioButton) rgAccess.getChildAt(i);
            if(button.getText().toString().equals(p.getHPrimaryImplantData().getAccessDirection())){
                button.setChecked(true);
            }
        }

        for (int i = 0; i < rgPosition.getChildCount(); i++){
            RadioButton button = (RadioButton) rgPosition.getChildAt(i);
            if(button.getText().toString().equals(p.getHPrimaryImplantData().getPosition())){
                button.setChecked(true);
            }
        }
    }

    /**
     * This method fills data from a patient object in the layout for Operation2.
     */
    private void fillOperationData2(){
        RadioGroup rgAdditionalInterventions = myView.findViewById(R.id.hp_rg_addInterventions);
        RadioGroup rgZementierTechnik = myView.findViewById(R.id.hp_rb_zementiertechnik);
        RadioGroup rgFixation = myView.findViewById(R.id.hp_rb_fixation);

        for(int i = 0; i < rgAdditionalInterventions.getChildCount(); i++){
            CheckBox box = (CheckBox) rgAdditionalInterventions.getChildAt(i);
            int storedAddInterventions = p.getHPrimaryImplantData().getAdditionalInterventionSize();

            for(int j = 0; j < storedAddInterventions; j++){
                if(box.getText().toString().equals(p.getHPrimaryImplantData().getAdditionalInterventions(j))){
                    box.setChecked(true);
                }
            }
        }

        for (int i = 0; i < rgZementierTechnik.getChildCount(); i++){
            RadioButton button = (RadioButton) rgZementierTechnik.getChildAt(i);
            String buttonLabel = "";
            if(p.getHPrimaryImplantData().getCementingType() == 1){
                buttonLabel = "erste Generation";
            } else if (p.getHPrimaryImplantData().getCementingType() == 2){
                buttonLabel = "zweite Generation";
            } else if (p.getHPrimaryImplantData().getCementingType() == 3){
                buttonLabel = "dritte Generation";
            }

            if(button.getText().toString().equals(buttonLabel)){
                button.setChecked(true);
            }
        }

        for (int i = 0; i < rgFixation.getChildCount(); i++){
            RadioButton button = (RadioButton) rgFixation.getChildAt(i);
            if(button.getText().toString().equals(p.getHPrimaryImplantData().getFixation())){
                button.setChecked(true);
            }
        }
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
        birthPlace.setText(p.getCityofbirth());
        birthCountry.setText(p.getBirthCountry());
        weight.setText(p.getWeightInKg() + " kg");
        height.setText(p.getHeightInCm() + " cm");
        asa.setText("ASA: " + p.getHPrimaryImplantData().getMORBIDITY_STATE());
        surgeryDate.setText(p.getSurgeryDate());
        firstSurgeon.setText(p.getFirstSurgeon());
        secondSurgeon.setText(p.getSecondSurgeon());
    }

    /**
     * Loads the retrieved Patient Object into the SharedPreferences on the Android System.
     *
     * THIS METHOD MAYBE NEEDS TO BE A STATIC METHOD IN A COMMON CLASS!!!! JAN
     *
     * @param p - Patient
     */
    private void
    loadPatientToSharedPref(Patient p, boolean patientLoaded){
        sp = getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
        editor = sp.edit();

        Gson gson = new Gson();
        String patientJson = gson.toJson(p);

        editor.putString("patient", patientJson);
        editor.putBoolean("loaded", patientLoaded);
        editor.apply();
        editor.commit();
    }
}
