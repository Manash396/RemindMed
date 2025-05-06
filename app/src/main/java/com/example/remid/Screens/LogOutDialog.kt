package com.example.remid.Screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable

fun LogoutDialog(
    onRefresh: () -> Unit,
    onSignout: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onRefresh() },
        title = { Text("Remid") },

        confirmButton = {
            Button(onClick = { onRefresh() }) {
                Text("Refresh")
            }
        },
        dismissButton = {
            Button(onClick = { onSignout() }) {
                Text("Sign Out")
            }
        }
    )
}
