package ru.itis.homework5.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import ru.itis.homework5.Fragment.MainFragment
import ru.itis.homework5.R

class MainActivity : AppCompatActivity() {

    private var singlePermissionResult: ActivityResultLauncher<String>? = null
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContract()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestSinglePermission(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commit()
    }

    fun openDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permissions))
            .setMessage(getString(R.string.allow_notifications))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                openAppSettings()
            }.show()
    }

    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = android.net.Uri.parse("package:$packageName")
        }
        startActivity(intent)
    }

    fun initContract() {
        singlePermissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Log.i("TAG HermissionHandler", "${isGranted}")
            if (!isGranted) {
                requestSinglePermission(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    fun requestSinglePermission(permission: String) {
        if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            return
        }
        count++
        Log.i("TAG HermissionHandler", "count = ${count}")
        if (count < 3) {
            singlePermissionResult?.launch(permission)
        } else { openDialog() }
    }
}