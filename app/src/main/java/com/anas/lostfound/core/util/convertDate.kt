package com.anas.lostfound.core.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("SimpleDateFormat")
fun getDateTime(s: Long): String? {
    return try {
        SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(s))
    } catch (e: Exception) {
        e.toString()
    }
}
