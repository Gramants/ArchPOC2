package com.ste.arch.entities;

import com.ste.arch.entities.pojos.Issue;

import java.util.List;

/**
 * Created by Stefano on 31/10/2017.
 */

public class IssueResponse {

    private List<Issue> results;

    public List<Issue> getResults() {
        return results;
    }

    public void setResults(List<Issue> results) {
        this.results = results;
    }
}
