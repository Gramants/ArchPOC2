package com.ste.arch.entities.pojos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Issue  {


    //@PrimaryKey(autoGenerate = true)
    //private int id;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("repository_url")
    @Expose
    private String repositoryUrl;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("state")
    @Expose
    private String state;
    @Expose
    private String createdAt;
    @SerializedName("body")
    @Expose
    private String body;

    private Boolean error;

    public Issue(String url, String repositoryUrl, Integer number, String title, String state, String createdAt, String body,int id) {
        this.url = url;
        this.repositoryUrl = repositoryUrl;
        this.number = number;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.body = body;
        this.id = id;
        this.error=false;
    }

    public Issue(Boolean error){
        this.error=error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    //public int getId() {
    //    return id;
    //}

    //public void setId(int id) {
    //     this.id = id;
    //}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getError() {
        return error;
    }

}