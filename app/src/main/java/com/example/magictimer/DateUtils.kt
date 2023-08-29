package com.example.magictimer

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    val currentDate = Date()
    return dateFormat.format(currentDate)
}
