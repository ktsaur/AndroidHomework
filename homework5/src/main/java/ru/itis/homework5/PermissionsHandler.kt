package ru.itis.homework5

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionsHandler(
    private val activity: AppCompatActivity,
    private val onSinglePermissionDenied: () -> Unit,
) {

    private var singlePermissionResult: ActivityResultLauncher<String>? = null

    fun initContract() {
        if (singlePermissionResult == null) {
            singlePermissionResult =
                this.activity?.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (!isGranted) {
                        onSinglePermissionDenied.invoke()
                    }
                }
        }
    }

    fun requestSinglePermission(permission: String) {
//        Log.i("TEST--TAG", "Вызывваем launch")
        singlePermissionResult?.launch(permission)
    }
}

