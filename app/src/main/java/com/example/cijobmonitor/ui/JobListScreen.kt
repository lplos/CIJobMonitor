package com.example.cijobmonitor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cijobmonitor.data.Job

@Composable
fun JobListScreen(jobs: List<Job>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(jobs) { job ->
            JobListItem(job)
        }
    }
}
@Composable
fun JobListItem(job: Job) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(MaterialTheme.shapes.small)
                .background(job.statusColor)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = job.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = job.status,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
