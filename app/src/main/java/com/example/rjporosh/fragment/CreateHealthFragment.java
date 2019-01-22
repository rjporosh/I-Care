package com.example.rjporosh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rjporosh.icare.ApplicationMain;
import com.example.rjporosh.icare.DatabaseHelper;
import com.example.arafathossain.icare.R;
import com.example.rjporosh.icare.HealthInformation;
//import com.example.arafathossain.fragment.;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jewel on 12/20/2015.
 */
public class CreateHealthFragment extends Fragment{
    EditText edit_temperature,edit_bloodpressure,edit_hight,edit_weight,edit_bmi,edit_calori;
    Toolbar toolbar;
    int profile_id;
    String date;
    HealthInformation healthInformation;
    DatabaseHelper healthDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.create_health_layout, container, false);
        profile_id=getArguments().getInt("profile_id");
        healthInformation=new HealthInformation();
        Date now=Calendar.getInstance().getTime();
        date=new SimpleDateFormat("yyyy-MM-dd").format(now);
        healthDatabase= ApplicationMain.getDatabase();
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Add Daily Health");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                saveData(profile_id);
                HealthInfoFragment healthInfoFragment =new HealthInfoFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("profile_id", profile_id);
                healthInfoFragment.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragmentContainer, healthInfoFragment);
                ft.commit();
                return true;
            }
        });
        edit_temperature=(EditText)view.findViewById(R.id.edit_temperature);
        edit_bloodpressure=(EditText)view.findViewById(R.id.edit_bp);
        edit_hight=(EditText)view.findViewById(R.id.edit_hight);
        edit_weight=(EditText)view.findViewById(R.id.edit_weight);
        edit_bmi=(EditText)view.findViewById(R.id.edit_bmi);
        edit_calori=(EditText)view.findViewById(R.id.edit_calori);
        try {
            showData(profile_id);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
            edit_calori.setText("Not Set");
            edit_bmi.setText("Not Set");
            edit_weight.setText("Not Set");
            edit_hight.setText("Not Set");
            edit_bloodpressure.setText("Not Set");
            edit_temperature.setText("Not Set");
        }
        return view;
    }

    private void showData(int profile_id) {
        try {
            if (healthDatabase!=null) {
                HealthInformation infoList = healthDatabase.getHealthData(String.valueOf(profile_id));
                    edit_bloodpressure.setText(infoList.getBloodPressure());
                    edit_temperature.setText(infoList.getTemperature());
                    edit_weight.setText(infoList.getSleep());
                    edit_hight.setText(infoList.getHeart_Rate());
                    edit_bmi.setText(infoList.getBmi());
                    edit_calori.setText(infoList.getCalorie());
            }
        }catch (NullPointerException e){

        }
    }

    private void saveData(int profile_id) {
        healthInformation.setBmi(edit_bmi.getText().toString());
        healthInformation.setCalorie(edit_calori.getText().toString());
        healthInformation.setBloodPressure(edit_bloodpressure.getText().toString());
        healthInformation.setHeart_Rate(edit_hight.getText().toString());
        if(edit_weight.getText().toString().equals("") || edit_weight.getText().toString().equals(null)){
            healthInformation.setSleep("Unknown");
        }else{
            healthInformation.setSleep(edit_weight.getText().toString());
        }
        healthInformation.setTemperature(edit_temperature.getText().toString());
        healthInformation.setProfileName(String.valueOf(profile_id));
        healthInformation.setDate(date);
        int r=healthDatabase.InsertHealthInfo(healthInformation);
        Toast.makeText(getActivity(),"Rows "+r,Toast.LENGTH_SHORT).show();
    }
}
