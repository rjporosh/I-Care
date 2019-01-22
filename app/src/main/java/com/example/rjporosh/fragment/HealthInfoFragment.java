
package com.example.rjporosh.fragment;

import com.example.rjporosh.icare.ApplicationMain;
import com.example.rjporosh.icare.DatabaseHelper;
import com.example.rjporosh.icare.HealthInformation;
import com.example.arafathossain.icare.R;
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
import android.widget.TextView;
import android.widget.Toast;

public class HealthInfoFragment extends Fragment {
    EditText edit_temperature,edit_bloodpressure,edit_hight,edit_weight,edit_bmi,edit_calori;
    Toolbar toolbar;
    TextView date;
    int profile_id;
    HealthInformation healthInformation;
    DatabaseHelper healthDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_info_layout, container, false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        healthDatabase=ApplicationMain.getDatabase();
        healthInformation=new HealthInformation();
        date=(TextView)view.findViewById(R.id.date);
        edit_temperature=(EditText)view.findViewById(R.id.edit_temperature);
        edit_bloodpressure=(EditText)view.findViewById(R.id.edit_bloodpressure);
        edit_hight=(EditText)view.findViewById(R.id.edit_hight);
        edit_weight=(EditText)view.findViewById(R.id.edit_weight);
        edit_bmi=(EditText)view.findViewById(R.id.edit_bmi);
        edit_calori=(EditText)view.findViewById(R.id.edit_calori);
        try{
            profile_id=getArguments().getInt("profile_id");
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
                toolbar.setTitle("Health");
                toolbar.inflateMenu(R.menu.add_item_menu);
                toolbar.setNavigationIcon(R.mipmap.back_button);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeProfileDetailFragment hf=new HomeProfileDetailFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragmentContainer, hf);
                ft.commit();
            }
        });
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.add_item) {
                            CreateHealthFragment createHealthFragment = new CreateHealthFragment();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            Bundle bundle = new Bundle();
                            bundle.putInt("profile_id", profile_id);
                            createHealthFragment.setArguments(bundle);
                            FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragmentContainer, createHealthFragment);
                            ft.commit();
                        }
                        return true;
                    }
                });
        return view;
    }

    private void showData(int profile_id) {
        try {
            if (healthDatabase!=null) {
                HealthInformation infoList=healthDatabase.getHealthData(String.valueOf(profile_id));
                    edit_bloodpressure.setText(infoList.getBloodPressure());
                    edit_temperature.setText(infoList.getTemperature());
                    edit_weight.setText(infoList.getSleep());
                    edit_hight.setText(infoList.getHeart_Rate());
                    edit_bmi.setText(infoList.getBmi());
                    edit_calori.setText(infoList.getCalorie());
                    date.setText(infoList.getDate());
                Toast.makeText(getActivity(), "BMI "+infoList.getBmi(), Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            edit_calori.setText("Not Set");
            edit_bmi.setText("Not Set");
            edit_weight.setText("Not Set");
            edit_hight.setText("Not Set");
            edit_bloodpressure.setText("Not Set");
            edit_temperature.setText("Not Set");
        }
    }
}
