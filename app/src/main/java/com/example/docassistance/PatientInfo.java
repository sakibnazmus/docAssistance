package com.example.docassistance;

import android.os.Parcel;
import android.os.Parcelable;

public class PatientInfo implements Parcelable {
    private int age;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private Gender gender;

    PatientInfo(String fName, String lName, int age, Gender gender, String phone){
        this.firstName = fName;
        this.lastName = lName;
        this.age = age;
        this.gender = gender;
        this.mobileNo = phone;
    }

    protected PatientInfo(Parcel in) {
        age = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        mobileNo = in.readString();
    }

    public static final Creator<PatientInfo> CREATOR = new Creator<PatientInfo>() {
        @Override
        public PatientInfo createFromParcel(Parcel in) {
            return new PatientInfo(in);
        }

        @Override
        public PatientInfo[] newArray(int size) {
            return new PatientInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeInt(age);
        parcel.writeInt(gender.ordinal());
        parcel.writeString(mobileNo);
    }
}
