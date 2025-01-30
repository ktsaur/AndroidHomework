package ru.itis.homework5.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ru.itis.homework5.Fragment.ComposeSampleFragment
import ru.itis.homework5.PermissionsHandler
import ru.itis.homework5.R

class MainActivity : AppCompatActivity() {

    val permissionsHandler = PermissionsHandler( this,
        onSinglePermissionDenied = { openDialog() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsHandler.initContract()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsHandler.requestSinglePermission(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ComposeSampleFragment())
            .commit()
    }


    fun openDialog() {
        AlertDialog.Builder(this)
            .setTitle("Разерешения")
            .setMessage("Разрешите отправку уведомлений")
            .setPositiveButton("Открыть настройки") { _, _ ->
                openAppSettings()
            }.show()
    }

    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = android.net.Uri.parse("package:$packageName")
        }
        startActivity(intent)
    }
}