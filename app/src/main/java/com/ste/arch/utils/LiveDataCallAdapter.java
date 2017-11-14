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

package com.ste.arch.utils;


import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.ste.arch.repositories.Resource;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapterthat converts the Call into a LiveData of ApiResponse.
 * @param <RequestType>
 */
public class LiveDataCallAdapter<RequestType> implements CallAdapter<RequestType, LiveData<Resource<RequestType>>> {
    private final Type responseType;
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<Resource<RequestType>> adapt(Call<RequestType> call) {
        return new LiveData<Resource<RequestType>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<RequestType>() {
                        @Override
                        public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                            Log.e("STEFANO","onresponse");
                            if (response.isSuccessful()) {
                                postValue(Resource.success(response.body()));
                            } else {

                                postValue(Resource.error(response.message(), null));

                            }

                        }

                        @Override
                        public void onFailure(Call<RequestType> call, Throwable throwable) {
                            postValue(Resource.error(throwable.getMessage(), null));
                        }
                    });
                }
            }
        };
    }
}
