package com.aadil.parcelabledemo;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentInfo implements Parcelable {

    private String Name;
    private String UniqueID;
    private int Marks;

    public StudentInfo(String name, String uniqueID, int marks) {
        Name = name;
        UniqueID = uniqueID;
        Marks = marks;
    }

    public String getName() {
        return Name;
    }

    public int getMarks() {
        return Marks;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    protected StudentInfo(Parcel in) {
        Name = in.readString();
        UniqueID = in.readString();
        Marks = in.readInt();
    }

    public static final Creator<StudentInfo> CREATOR = new Creator<StudentInfo>() {
        @Override
        public StudentInfo createFromParcel(Parcel in) {
            return new StudentInfo(in);
        }

        @Override
        public StudentInfo[] newArray(int size) {
            return new StudentInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(UniqueID);
        dest.writeInt(Marks);
    }
}
