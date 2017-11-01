package com.ste.arch.entities;

/**
 * Created by Stefano on 18/10/2017.
 */
public class QueryString {

    private String user;
    private String repo;
    private Boolean forceremote;


    public String getUser() {
        return user;
    }

    public String getRepo() {
        return repo;
    }


    public Boolean getForceremote() {
        return forceremote;
    }


    public QueryString(String user, String repo, Boolean forceremote) {

        this.user = user;
        this.repo = repo;
        this.forceremote = forceremote;
    }


}
