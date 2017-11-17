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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.pojos.Issue;
import com.ste.arch.repositories.Resource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiUtil {
    public static <T> LiveData<Resource<T>> successResourceCall(T data) {
        return createResourceCall(Response.success(data));
    }
    public static <T> LiveData<Resource<T>> createResourceCall(Response<T> response) {
        MutableLiveData<Resource<T>> data = new MutableLiveData<>();
        data.setValue(Resource.success(response.body()));
        return data;
    }

    public static <T> LiveData<T> successCall(T results) {
        MutableLiveData<T> data = new MutableLiveData<>();
        data.setValue(results);
        return data;
    }


}
