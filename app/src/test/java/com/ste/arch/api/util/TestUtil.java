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

package com.ste.arch.api.util;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.pojos.Issue;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {


    public static List<IssueDataModel> createIssueDataModelList(int count, String title, String body) {
        List<IssueDataModel> issues = new ArrayList<>();
        for(int i = 0; i < count; i ++) {
            issues.add(createIssueDataModel(title + i, body + i));
        }
        return issues;
    }

    public static IssueDataModel createIssueDataModel(String title,String body) {
        return new  IssueDataModel("ste", "ste", 1, title, "ste", "ste", body, "ste", "ste");
    }

    public static List<Issue> createIssueList(int count, String title, String body) {
        List<Issue> issues = new ArrayList<>();
        for(int i = 0; i < count; i ++) {
            issues.add(createIssue(title + i, body + i));
        }
        return issues;
    }

    public static Issue createIssue(String title,String body) {
        return new Issue ("ste", "ste", 1, title, "ste", "ste", title,1);

        }

}
