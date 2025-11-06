package com.example.cijobmonitor.ui

import androidx.compose.ui.graphics.Color
import com.example.cijobmonitor.data.Job

val Job.statusColor: Color
    get() = when {
        color == null -> Color.Gray
        color.contains("blue") -> Color(0xFF4CAF50) // green
        color.contains("red") -> Color(0xFFF44336) // red
        color.contains("yellow") -> Color(0xFFFFC107) // amber
        //color.contains("anime") -> Color(0xFF2196F3) // blue (running)
        else -> Color.LightGray
    }
