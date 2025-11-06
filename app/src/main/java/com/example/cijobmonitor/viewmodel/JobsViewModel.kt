package com.example.cijobmonitor.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cijobmonitor.R
import com.example.cijobmonitor.data.Job
import com.example.cijobmonitor.data.JobsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JobsViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = JobsRepository()
    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> = _jobs.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private var soundPlayer: MediaPlayer? = null
    private val lastStatuses = mutableMapOf<String, String?>()
    private var initialized = false
    init {
        startPolling()
    }
    private fun startPolling() {
        viewModelScope.launch {
            val endpoint = "https://ci-builds.apache.org/job/Ant/api/json"
            while (true) {
                try {
                    _isLoading.value = true
                    val newJobs = repository.fetchJobs(endpoint)
                    _jobs.value = newJobs

                    if (initialized) {
                        detectNewFailures(newJobs)
                    } else {
                        initialized = true
                    }

                    newJobs.forEach { job ->
                        lastStatuses[job.name] = job.color
                    }

                } catch (e: Exception) {
                    Log.e("JobsViewModel", "Error fetching jobs", e)
                } finally {
                    _isLoading.value = false
                }

                delay(30_000)
            }
        }
    }
    private fun detectNewFailures(newJobs: List<Job>) {
        val newlyFailed = newJobs.filter { job ->
            val oldColor = lastStatuses[job.name]
            val newColor = job.color
            oldColor != null && oldColor != newColor && newColor?.contains("red") == true
        }

        if (newlyFailed.isNotEmpty()) {
            Log.d("JobsViewModel", "New failures: ${newlyFailed.map { it.name }}")
            playWarning()
        }
    }

    private fun playWarning() {
        try {
            soundPlayer?.release()
            soundPlayer = MediaPlayer.create(getApplication(), R.raw.doh1_y)
            soundPlayer?.setOnCompletionListener { it.release() }
            soundPlayer?.start()
        } catch (e: Exception) {
            Log.e("JobsViewModel", "Error playing sound", e)
        }
    }
    ///Simulate turning any job status red
    fun forceFailJob(index: Int) {
        val currentJobs = _jobs.value.toMutableList()
        if (index in currentJobs.indices) {
            val target = currentJobs[index]
            if (target.color?.contains("red") == true) {
                Log.d("JobsViewModel", "Job '${target.name}' already red.")
                return
            }

            val modified = target.copy(color = "red")
            currentJobs[index] = modified
            _jobs.value = currentJobs

            detectNewFailures(currentJobs)
            lastStatuses[target.name] = "red"
        }
    }
    override fun onCleared() {
        super.onCleared()
        soundPlayer?.release()
        soundPlayer = null
    }
}
