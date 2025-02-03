package ru.itis.homework5.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class PermissionsHandler(
    private val activity: AppCompatActivity,
    private val onSinglePermissionDenied: () -> Unit,
) {
    private var singlePermissionResult: ActivityResultLauncher<String>? = null
    private var count = 0

    fun initContract() {
        if (singlePermissionResult == null) {
            singlePermissionResult = this.activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (!isGranted) {
                    count++
                    if (count < 3) {
                        requestSinglePermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        onSinglePermissionDenied.invoke()
                    }
                }
            }
        }
    }

    private fun requestSinglePermission(permission: String) {
        singlePermissionResult?.launch(permission)
    }
}

