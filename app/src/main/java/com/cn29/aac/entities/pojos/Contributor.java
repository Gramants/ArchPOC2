package com.cn29.aac.entities.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Stefano on 07/10/2017.
 */

public class Contributor {

    //@PrimaryKey(autoGenerate = true)
    //private int id;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("html_url")
    @Expose
    private String html_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Contributor(int id, String login, String html_url) {

        this.id = id;
        this.login = login;
        this.html_url = html_url;
    }
}
