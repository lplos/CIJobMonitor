package com.example.cijobmonitor.data

import com.example.cijobmonitor.network.CiApi
import com.example.cijobmonitor.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.cijobmonitor.ui.statusColor
class JobsRepository(
    private val api: CiApi = NetworkModule.ciApi
) {
    suspend fun fetchJobs(endpointUrl: String): List<Job> = withContext(Dispatchers.IO) {
        val response = api.jobsJson(endpointUrl)
        response.jobs.map { dto ->
            Job(
                name = dto.name,
                url = dto.url,
                color = dto.color ?: "unknown"
            )
        }
    }
}
