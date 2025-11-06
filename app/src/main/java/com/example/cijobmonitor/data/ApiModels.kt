package com.example.cijobmonitor.data

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class JobsResponse(
    val jobs: List<JobDto> = emptyList()
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class JobDto(
    val name: String,
    val url: String,
    val color: String? = "unknown"
)
data class Job(
    val name: String,
    val url: String,
    val color: String? = null
) {
    val status: String
        get() = when {
            color == null -> "NOT BUILT"
            color.contains("blue") -> "SUCCESS"
            color.contains("red") -> "FAILED"
            color.contains("yellow") -> "UNSTABLE"
            //color.contains("anime") -> "RUNNING"
            else -> "UNKNOWN"
        }
    val isFailing: Boolean
        get() = color?.contains("red") == true
}
