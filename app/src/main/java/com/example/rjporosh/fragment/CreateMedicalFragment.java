package com.example.rjporosh.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.rjporosh.icare.ApplicationMain;
import com.example.rjporosh.icare.DatabaseHelper;
import com.example.rjporosh.icare.MedicalInformation;
import com.example.arafathossain.icare.R;

/**
 * Created by Jewel on 12/22/2015.
 */
public class CreateMedicalFragment extends Fragment {
    ImageView editmedicalPicture;
    Toolbar toolbar;
    ImageButton camera;
    ImageButton gallary;
    int profile_id;
    EditText editMedicalName,editMedicalAddress,editMedicalContacts,editMedicalEmail,editMedicalWebsite;
    MedicalInformation medicalInformation;
    DatabaseHelper medicalDetabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.create_medical_layout,container,false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        medicalInformation=new MedicalInformation();
        medicalDetabase= ApplicationMain.getDatabase();
        editmedicalPicture=(ImageView)view.findViewById(R.id.picture_medical);
        camera=(ImageButton)view.findViewById(R.id.camera);
        gallary=(ImageButton)view.findViewById(R.id.gallary);
        editMedicalName=(EditText)view.findViewById(R.id.edit_medical_name);
        editMedicalAddress=(EditText)view.findViewById(R.id.edit_medical_address);
        editMedicalContacts=(EditText)view.findViewById(R.id.edit_medical_contacts);
        editMedicalEmail=(EditText)view.findViewById(R.id.edit_medical_email);
        editMedicalWebsite=(EditText)view.findViewById(R.id.edit_medical_website);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1);
                }
            }
        });
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fileIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(fileIntent, "select file"), 2);
            }
        });
        try{
            profile_id=getArguments().getInt("profile_id");
            showData();
        }catch (NullPointerException e)
        {
            editmedicalPicture.setImageResource(R.mipmap.noimage);
            editMedicalName.setText("Not Set");
            editMedicalAddress.setText("Not Set");
            editMedicalContacts.setText("Not Set");
            editMedicalEmail.setText("Not Set");
            editMedicalWebsite.setText("Not Set");
        }
        toolbar.setTitle("Add Medical Info");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.save_profile)
                {
                    saveData();
                    MedicalInfoFragment medicalInfoFragment = new MedicalInfoFragment();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("profile_id", profile_id);
                    medicalInfoFragment.setArguments(bundle);
                    FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragmentContainer, medicalInfoFragment);
                    ft.commit();

                }
                return true;
            }
        });
        return view;
    }

    private void saveData() {
        medicalInformation.setMedicalName(editMedicalName.getText().toString());
        medicalInformation.setMedicalAdress(editMedicalAddress.getText().toString());
        medicalInformation.setMedicalContact(editMedicalContacts.getText().toString());
        medicalInformation.setMedicalEmail(editMedicalEmail.getText().toString());
        medicalInformation.setMedicalWeb(editMedicalWebsite.getText().toString());
        medicalInformation.setMedicalPic(BitmapFactory.decodeResource(getResources(),R.mipmap.noimage));
        medicalInformation.setProfileId(String.valueOf(profile_id));
        medicalDetabase.InsertMedicalInfo(medicalInformation);
    }

    private void showData() {
        if (medicalInformation!=null)
        {
            medicalInformation=medicalDetabase.getMedicalData(profile_id);
            editMedicalName.setText(medicalInformation.getMedicalName());
            editMedicalAddress.setText(medicalInformation.getMedicalAdress());
            editMedicalContacts.setText(medicalInformation.getMedicalContact());
            editMedicalEmail.setText(medicalInformation.getMedicalEmail());
            editMedicalWebsite.setText(medicalInformation.getMedicalWeb());
            editmedicalPicture.setImageBitmap(medicalInformation.getMedicalPic());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 1) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                editmedicalPicture.setImageBitmap(imageBitmap);
            } else if (requestCode == 2) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                editmedicalPicture.setImageBitmap(bm);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
