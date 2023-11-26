package com.behzad.gituserfinder.features.shared

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun ToastMessage(toastMessage: String) {
    val context = LocalContext.current
    LaunchedEffect(toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
}