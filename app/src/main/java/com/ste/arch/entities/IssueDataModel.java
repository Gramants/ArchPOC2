package com.ste.arch.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.ste.arch.Config;


@Entity(tableName = Config.ISSUES_TABLE_NAME)
public class IssueDataModel implements Parcelable {

    public static DiffCallback<IssueDataModel> DIFF_CALLBACK = new DiffCallback<IssueDataModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull IssueDataModel oldItem, @NonNull IssueDataModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull IssueDataModel oldItem, @NonNull IssueDataModel newItem) {
            return oldItem.equals(newItem);
        }
    };



    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        IssueDataModel user = (IssueDataModel) obj;

        return user.getId() == this.getId() && user.getTitle() == this.getTitle();
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "number")
    private Integer number;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "repositoryurl")
    private String repositoryUrl;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "userurl")
    private String userurl;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "createdat")
    private String createdAt;
    @ColumnInfo(name = "body")
    private String body;


    public IssueDataModel(String url, String repositoryUrl, Integer number, String title, String state, String createdAt, String body, String username, String userurl) {
        this.url = url;
        this.repositoryUrl = repositoryUrl;
        this.number = number;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.body = body;
        this.username = username;
        this.userurl = userurl;
    }


    protected IssueDataModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            number = null;
        } else {
            number = in.readInt();
        }
        url = in.readString();
        repositoryUrl = in.readString();
        title = in.readString();
        username = in.readString();
        userurl = in.readString();
        state = in.readString();
        createdAt = in.readString();
        body = in.readString();
    }

    public static final Creator<IssueDataModel> CREATOR = new Creator<IssueDataModel>() {
        @Override
        public IssueDataModel createFromParcel(Parcel in) {
            return new IssueDataModel(in);
        }

        @Override
        public IssueDataModel[] newArray(int size) {
            return new IssueDataModel[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserurl() {
        return userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number);
        }
        dest.writeString(url);
        dest.writeString(repositoryUrl);
        dest.writeString(title);
        dest.writeString(username);
        dest.writeString(userurl);
        dest.writeString(state);
        dest.writeString(createdAt);
        dest.writeString(body);
    }
}