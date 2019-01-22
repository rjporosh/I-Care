package com.example.rjporosh.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rjporosh.icare.ApplicationMain;
import com.example.rjporosh.icare.DatabaseHelper;
import com.example.rjporosh.icare.MedicalInformation;
import com.example.arafathossain.icare.R;


/**
 * Created by Jewel on 12/22/2015.
 */
public class MedicalInfoFragment extends Fragment {
    ImageView medicalImage;
    Button mapButton;
    Toolbar toolbar;
    ImageButton callButton,browserButton;
    TextView medicalName,medicalAddress,mediacContacts,medicalEmail,medicalWebsite;
    int profile_id;
    MedicalInformation medicalInformation;
    DatabaseHelper medicalDetabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.medical_info_layout,container,false);
        medicalInformation=new MedicalInformation();
        medicalDetabase= ApplicationMain.getDatabase();
        medicalImage=(ImageView)view.findViewById(R.id.picture_medical);
        mapButton=(Button)view.findViewById(R.id.medical_map_button);
        callButton=(ImageButton)view.findViewById(R.id.call_button);
        browserButton=(ImageButton)view.findViewById(R.id.browser_button);
        medicalName=(TextView)view.findViewById(R.id.medical_name);
        medicalAddress=(TextView)view.findViewById(R.id.medical_address);
        mediacContacts=(TextView)view.findViewById(R.id.medical_contacts);
        medicalEmail=(TextView)view.findViewById(R.id.medical_email);
        medicalWebsite=(TextView)view.findViewById(R.id.medical_website);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Medical");
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
        try{
            profile_id=getArguments().getInt("profile_id");
            showData();
            toolbar.inflateMenu(R.menu.edit_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit_item: {
                            Fragment createMedicalFragment = new CreateMedicalFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("profileId", profile_id);
                            createMedicalFragment.setArguments(bundle);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragmentContainer, createMedicalFragment);
                            ft.commit();
                        }
                        break;
                    }
                    return true;
                }
            });
        }catch (NullPointerException e)
        {
            toolbar.inflateMenu(R.menu.add_item_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.add_item: {
                            CreateMedicalFragment createMedicalFragment = new CreateMedicalFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("profileId", profile_id);
                            createMedicalFragment.setArguments(bundle);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragmentContainer, createMedicalFragment);
                            ft.commit();
                        }
                        break;
                    }
                    return true;
                }
            });
        }
        return view;
    }
    public void medical (View view)
    {
        switch (view.getId())
        {
            case R.id.call_button:
            {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+mediacContacts.getText().toString()));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Toast.makeText(getActivity(), "Call failed.", Toast.LENGTH_SHORT);
                }
            }break;
            case R.id.browser_button:
            {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    Uri u = Uri.parse(medicalWebsite.getText().toString());
                    i.setData(u);
                }catch (ActivityNotFoundException e)
                {
                    Toast.makeText(getActivity(), "Browser not found.", Toast.LENGTH_SHORT);
                }
            }break;
            case R.id.medical_map_button:
            {

            }
        }
    }

    private void showData() {
        try {
            if (medicalInformation!=null)
            {
                medicalInformation=medicalDetabase.getMedicalData(profile_id);
                medicalName.setText(medicalInformation.getMedicalName());
                medicalAddress.setText(medicalInformation.getMedicalAdress());
                mediacContacts.setText(medicalInformation.getMedicalContact());
                medicalEmail.setText(medicalInformation.getMedicalEmail());
                medicalWebsite.setText(medicalInformation.getMedicalWeb());
                medicalImage.setImageBitmap(medicalInformation.getMedicalPic());
            }
        }catch (NullPointerException e)
        {
            medicalImage.setImageResource(R.mipmap.noimage);
            medicalName.setText("Not Set");
            medicalAddress.setText("Not Set");
            mediacContacts.setText("Not Set");
            medicalEmail.setText("Not Set");
            medicalWebsite.setText("Not Set");
        }
    }

}
