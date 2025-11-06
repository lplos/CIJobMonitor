package com.example.cijobmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.cijobmonitor.data.Job
import com.example.cijobmonitor.ui.statusColor
import com.example.cijobmonitor.ui.theme.CIJobMonitorTheme
import com.example.cijobmonitor.viewmodel.JobsViewModel
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<JobsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CIJobMonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JobsScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(viewModel: JobsViewModel) {
    val jobs by viewModel.jobs.collectAsState()
    val loading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("CI Job Monitor") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(jobs) { _, job ->
                    JobItem(
                        job = job
                        // Test button per job
                    )
                }
            }
        }
    }
}
@Composable
fun JobItem(
    job: Job
) {
    Surface(
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(job.statusColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = job.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = job.status, style = MaterialTheme.typography.bodyMedium)
            }

//      Testing For Every Job - Adds Fail Button That Temp Changes Status to Red
//            Button(
//                onClick = onSimulateFail,
//                modifier = Modifier.height(36.dp)
//            ) {
//                Text("Fail")
//            }
        }
    }
}
