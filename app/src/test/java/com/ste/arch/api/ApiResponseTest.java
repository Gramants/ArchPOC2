
package com.ste.arch.api;

import com.ste.arch.repositories.Resource;
import com.ste.arch.repositories.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;



@RunWith(JUnit4.class)
public class ApiResponseTest {
    @Test
    public void exception() {

        Resource apiResponse = new Resource(Status.ERROR,"error","errormessage");
        assertThat(apiResponse.data, notNullValue());
        assertThat(apiResponse.message, notNullValue());
        assertThat(apiResponse.status, is(Status.ERROR));
    }

    @Test
    public void success() {
        Resource apiResponse = new Resource(Status.SUCCESS,"somedata",null);
        assertThat(apiResponse.data, notNullValue());
        assertThat(apiResponse.message, is(nullValue()));
        assertThat(apiResponse.status, is(Status.SUCCESS));
    }


}