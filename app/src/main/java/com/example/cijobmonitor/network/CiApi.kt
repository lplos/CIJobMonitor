package com.example.cijobmonitor.network

import com.example.cijobmonitor.data.JobsResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface CiApi {
    @GET
    suspend fun jobsJson(@Url url: String): JobsResponse
}
