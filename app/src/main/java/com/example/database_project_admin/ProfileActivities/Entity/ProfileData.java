package com.example.database_project_admin.ProfileActivities.Entity;

public class ProfileData {

    String profileDataID;

    String name;
    String CNIC;
    String email;
    String Date_of_birth;
    String cell_number;
    String Education;

    public ProfileData() {
    }

    public ProfileData(String profileDataID, String name, String CNIC, String email, String date_of_birth, String cell_number, String education) {
        this.profileDataID = profileDataID;

        this.name = name;
        this.CNIC = CNIC;
        this.email = email;
        this.Date_of_birth = date_of_birth;
        this.cell_number = cell_number;
        this.Education = education;
    }

    public String getProfileDataID() {
        return profileDataID;
    }

    public void setProfileDataID(String profileDataID) {
        this.profileDataID = profileDataID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_of_birth() {
        return Date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        Date_of_birth = date_of_birth;
    }

    public String getCell_number() {
        return cell_number;
    }

    public void setCell_number(String cell_number) {
        this.cell_number = cell_number;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }
}
