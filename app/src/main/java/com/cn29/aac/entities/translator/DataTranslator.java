package com.cn29.aac.entities.translator;

import com.cn29.aac.entities.ContributorDataModel;
import com.cn29.aac.entities.IssueDataModel;
import com.cn29.aac.entities.pojos.Contributor;
import com.cn29.aac.entities.pojos.Issue;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Stefano on 29/09/2017.
 */

public class DataTranslator {

    public static ArrayList<IssueDataModel> IssueTranslator(Response<List<Issue>> response) {
        ArrayList<IssueDataModel> transformed = new ArrayList();
            for (Issue listitem : response.body()) {
                transformed.add(new IssueDataModel(listitem.getId(), listitem.getUrl(), listitem.getRepositoryUrl(), listitem.getNumber(), listitem.getTitle(), listitem.getState(), listitem.getCreatedAt(), listitem.getBody(), listitem.getUser().getLogin(), listitem.getUser().getUrl()));
            }
        return transformed;
    }

    public static ArrayList<ContributorDataModel> ContributorTranslator(Response<List<Contributor>> response) {
        ArrayList<ContributorDataModel> transformed = new ArrayList();
            for (Contributor listitem : response.body()) {
                transformed.add(new ContributorDataModel(listitem.getId(), listitem.getLogin(), listitem.getHtml_url()));
            }
        return transformed;
    }


}
