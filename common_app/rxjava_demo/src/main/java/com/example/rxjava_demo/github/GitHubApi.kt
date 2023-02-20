package com.example.rxjava_demo.github

import com.example.rxjava_demo.bean.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @get:GET("/users/FreedomPeace")
    val freedomPeace: Single<UserResponse>

    @GET("/users/FreedomPeace")
   suspend fun  freedomPeace2(): UserResponse

    @get:GET("/users")
    val users: Single<List<UserResponse>>

    @GET("/repos/{user}/bootstrap")
    fun getReposInfo(@Path("user") username: String?): Single<UserResponse>
}