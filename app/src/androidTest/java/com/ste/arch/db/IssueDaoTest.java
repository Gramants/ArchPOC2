/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ste.arch.db;

import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteException;
import android.support.test.runner.AndroidJUnit4;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.util.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.ste.arch.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class IssueDaoTest extends DbTest {
    @Test
    public void insertAndReadSingleRecord() throws InterruptedException {
        IssueDataModel issue = TestUtil.createRepoDataModel("foo", "bar", 1);
        db.issueDao().insertRecord(issue);
        IssueDataModel loaded = getValue(db.issueDao().getIssueById(1));
        assertThat(loaded, notNullValue());
        assertThat(loaded.getTitle(), is("foo"));
        assertThat(loaded.getBody(), is("bar"));

    }

    @Test
    public void insertAndReadListOfRecords() throws InterruptedException {
        List<IssueDataModel> issue = TestUtil.createIssuesDataModel(2, "foo", "bar");
        db.issueDao().insert(issue);
        LiveData<List<IssueDataModel>> data = db.issueDao().getAllIssue();
        assertThat(getValue(data).size(), is(2));

    }

    @Test
    public void updateRecords() throws InterruptedException {
        List<IssueDataModel> issue = TestUtil.createIssuesDataModel(2, "foo", "bar");
        db.issueDao().insert(issue);
        LiveData<List<IssueDataModel>> data = db.issueDao().getAllIssue();
        assertThat(getValue(data).size(), is(2));

        db.issueDao().deleteAll();

        List<IssueDataModel> issuenew = TestUtil.createIssuesDataModel(1, "update", "updated");
        db.issueDao().insert(issuenew);
        List<IssueDataModel> datanew = getValue(db.issueDao().getAllIssue());

        IssueDataModel last = datanew.get(0);
        assertThat(last.getTitle(), is("update0"));

    }

}
