package com.ste.arch.repositories.database.asyncdml;

import android.os.AsyncTask;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.repositories.database.ProjectDb;

import java.util.List;


/**
 * Created by Stefano on 26/09/2017.
 */

public class IssueDbManager {

    public static class AddIssueAsyncTask extends AsyncTask<List<IssueDataModel>, Void, Void> {

        private ProjectDb db;

        public AddIssueAsyncTask(ProjectDb userDatabase) {
            db = userDatabase;
        }

        @Override
        protected Void doInBackground(List<IssueDataModel>... issues) {
            db.issueDao().deleteAll();
            db.issueDao().insert(issues[0]);
            return null;
        }
    }

    public static class DeleteIssueByIdAsyncTask  extends AsyncTask<Integer, Void, Void> {
        private ProjectDb db;
        public DeleteIssueByIdAsyncTask(ProjectDb userDatabase) {db = userDatabase;}

        @Override
        protected Void doInBackground(Integer... integers) {
            db.issueDao().deleteById(integers[0]);
            return null;
        }
    }
}
