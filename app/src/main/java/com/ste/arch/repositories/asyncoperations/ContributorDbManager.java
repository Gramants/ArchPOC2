package com.ste.arch.repositories.asyncoperations;

import android.os.AsyncTask;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.repositories.database.ProjectDb;

import java.util.List;


/**
 * Created by Stefano on 26/09/2017.
 */

public class ContributorDbManager {

    public static class AddContributorsAsyncTask extends AsyncTask<List<ContributorDataModel>, Void, Void> {

        private ProjectDb db;

        public AddContributorsAsyncTask(ProjectDb userDatabase) {
            db = userDatabase;
        }

        @Override
        protected Void doInBackground(List<ContributorDataModel>... contributors) {
            db.contributorDao().deleteAll();
            db.contributorDao().insert(contributors[0]);
            return null;

        }
    }

    public static class DeleteContributorByIdAsyncTask  extends AsyncTask<Integer, Void, Void> {
        private ProjectDb db;
        public DeleteContributorByIdAsyncTask(ProjectDb userDatabase) {db = userDatabase;}

        @Override
        protected Void doInBackground(Integer... integers) {
            db.contributorDao().deleteById(integers[0]);
            return null;
        }
    }
}
