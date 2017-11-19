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

package com.ste.arch.util;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.pojos.Issue;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {



    public static List<Issue> createIssues(int count, String title, String body){
        List<Issue> repos = new ArrayList<>();
        for(int i = 0; i < count; i ++) {
            repos.add(createRepo(title+ i, body + i, i));
        }
        return repos;
    }

    public static Issue createRepo(String title, String body, int i) {
        return new Issue(title,body,i);
    }

    public static List<IssueDataModel> createIssuesDataModel(int count, String title, String body){
        List<IssueDataModel> repos = new ArrayList<>();
        for(int i = 0; i < count; i ++) {
            repos.add(createRepoDataModel(title+ i, body + i, i));
        }
        return repos;
    }

    public static IssueDataModel createRepoDataModel(String title, String body, int i) {
        return new IssueDataModel("","",i,title,"","",body,"","");
    }

}
