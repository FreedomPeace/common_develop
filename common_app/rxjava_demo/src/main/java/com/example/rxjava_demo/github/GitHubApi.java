package com.example.rxjava_demo.github;

import com.example.rxjava_demo.bean.UserResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApi {
    @GET("/users/FreedomPeace")
    Single<UserResponse> getFreedomPeace();

    @GET("/users")
    Single<List<UserResponse>> getUsers();

    @GET("/repos/{user}/bootstrap")
    Single<Object> getReposInfo(@Path("user") String username);
}
