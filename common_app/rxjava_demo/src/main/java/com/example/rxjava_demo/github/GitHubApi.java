package com.example.rxjava_demo.github;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface GitHubApi {
    @GET("/users/defunkt")
    Single<Object> getDefunkt();
}
