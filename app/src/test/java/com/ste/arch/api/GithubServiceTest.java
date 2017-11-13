
package com.ste.arch.api;


import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.ste.arch.entities.pojos.Issue;
import com.ste.arch.repositories.api.GithubApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GithubServiceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private GithubApiService service;

    private MockWebServer mockWebServer;


    //https://github.com/square/retrofit/blob/master/retrofit-adapters/java8/src/test/java/retrofit2/adapter/java8/CompletableFutureTest.java
    //https://github.com/sliskiCode/Robust-unit-testing-in-Android/blob/master/RobustUnitTestingInAndroid/src/main/kotlin/com/code/sliski/postlistscreen/di/PostListModule.kt
//https://blog.davidmedenjak.com/android/2016/11/22/mocking-api-calls.html
    //http://thedeveloperworldisyours.com/android/test-server-call-mockito-retrofit-rxjava/
    //https://github.com/codepath/android_guides/wiki/Consuming-APIs-with-Retrofit

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubApiService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getAllIssues() throws IOException, InterruptedException {
        enqueueResponse("issues.json");


        service.getIssues("test", "test").enqueue(new Callback<List<Issue>>() {


            @Override
            public void onResponse(Call<List<Issue>> call, retrofit2.Response<List<Issue>> response) {
                assertThat(response.body(), notNullValue());
                assertThat(response.body().size(), is(1));
                Issue issue = response.body().get(0);
                assertThat(issue.getTitle(), is("Found a bug"));

            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {

            }
        });


        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/repos/test/test/issues"));

    }


    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);

        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }


}
