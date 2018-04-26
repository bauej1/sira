package com.sira.sira;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.opencsv.CSVReader;
import com.sira.sira.databinding.MasterdataBinding;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jan on 05/04/2018.
 */

public class MasterDataActivity extends Fragment{

    private EditText patId;
    private View myView;
    private MasterdataBinding binding;
    private Patient p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.masterdata, null, false);

        TypedArray hipLayouts = getResources().obtainTypedArray(R.array.hiplayouts);
        int layoutId = hipLayouts.getResourceId(0, 0);

        myView = inflater.inflate(layoutId, container, false);
        myView = binding.getRoot();

        patId = myView.findViewById(R.id.patId);
        patId.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent){
                if (i == EditorInfo.IME_ACTION_DONE) {
                    p = getPatientData(patId.getText().toString());
                    binding.setPatient(p);
                }
                return false;
            }
        });

        return myView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    private Patient getPatientData(String editPatId){
        if(editPatId == null){
            return null;
        }

        CSVReader reader = getDummyDataFile();
        String[] nextValue;

        try {
            while((nextValue = reader.readNext()) != null){
                if(nextValue[0].equals(editPatId)){
                    return new Patient(Integer.parseInt(editPatId), nextValue[1], nextValue[2], nextValue[3], nextValue[4], nextValue[5], nextValue[6], nextValue[7], nextValue[8], Integer.parseInt(nextValue[9]), Integer.parseInt(nextValue[10]), Boolean.parseBoolean(nextValue[11]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CSVReader getDummyDataFile(){
        File file = new File(Environment.getExternalStorageDirectory() + "/Documents/dummyPatients.csv");
        try {
            return new CSVReader(new FileReader(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
