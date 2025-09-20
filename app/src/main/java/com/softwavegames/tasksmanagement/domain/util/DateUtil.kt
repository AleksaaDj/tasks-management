package com.softwavegames.tasksmanagement.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    fun calculateDaysLeft(dueDate: String?): Int {
        if (dueDate == null) return 0
        
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val due = dateFormat.parse(dueDate)
            val today = Date()
            val diffInMillis = due?.time?.minus(today.time)
            val diffInDays = diffInMillis?.div((24 * 60 * 60 * 1000))
            diffInDays?.toInt()?.coerceAtLeast(0) ?: 0
        } catch (_: Exception) {
            0
        }
    }

    fun formatDate(dateString: String?): String {
        if (dateString == null) return "No date"
        
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            if (date != null) {
                outputFormat.format(date)
            } else {
                "Invalid date"
            }
        } catch (_: Exception) {
            dateString
        }
    }
}