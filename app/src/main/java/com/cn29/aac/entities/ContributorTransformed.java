package com.cn29.aac.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class ContributorTransformed implements Parcelable {
    private String title;

    public ContributorTransformed(String title) {
        this.title = title;
    }

    protected ContributorTransformed(Parcel in) {
        title = in.readString();
    }

    public static final Creator<ContributorTransformed> CREATOR = new Creator<ContributorTransformed>() {
        @Override
        public ContributorTransformed createFromParcel(Parcel in) {
            return new ContributorTransformed(in);
        }

        @Override
        public ContributorTransformed[] newArray(int size) {
            return new ContributorTransformed[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
