package com.example.omdb.util

import android.content.Context
import android.widget.Toast

fun Context.showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}