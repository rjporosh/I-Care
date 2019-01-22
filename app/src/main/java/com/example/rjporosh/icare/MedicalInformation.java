package com.example.rjporosh.icare;

import android.graphics.Bitmap;

/**
 * Created by HP on 1/2/2016.
 */
public class MedicalInformation {

    private String medicalName;
    private String medicalEmail;
    private String medicalWeb;
    private String medicalAdress;
    private String medicalContact;
    private String profileId;
    private Bitmap medicalPic;
    public void setMedicalPic(Bitmap medicalPic) {
        this.medicalPic = medicalPic;
    }
    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    public void setMedicalEmail(String medicalEmail) {
        this.medicalEmail = medicalEmail;
    }

    public void setMedicalWeb(String medicalWeb) {
        this.medicalWeb = medicalWeb;
    }

    public void setMedicalAdress(String medicalAdress) {
        this.medicalAdress = medicalAdress;
    }

    public void setMedicalContact(String medicalContact) {
        this.medicalContact = medicalContact;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getMedicalName() {
        return medicalName;
    }

    public String getMedicalEmail() {
        return medicalEmail;
    }

    public String getMedicalWeb() {
        return medicalWeb;
    }

    public String getMedicalAdress() {
        return medicalAdress;
    }

    public String getMedicalContact() {
        return medicalContact;
    }

    public String getProfileId() {
        return profileId;
    }

    public Bitmap getMedicalPic() {
        return medicalPic;
    }
}
