package com.derra.myplantsapp.add_edit_plant

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import javax.inject.Inject

class PermissionHandler @Inject constructor (private val context: Context) {

    init {
        Log.d("PermissionHandler", "Received context: $context")
    }
    fun checkGalleryPermission(): Boolean {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val result = ContextCompat.checkSelfPermission(context, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }


    @Composable
    fun requestGalleryPermission(activity: ComponentActivity, requestCode: Int) {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, handle accordingly (e.g., trigger image selection)
                } else {
                    // Permission denied, handle accordingly (e.g., show an error message)
                }
            }
        )

        val scope = rememberCoroutineScope()
        //scope.launch {
        //    launcher.launch(permission)
        //}
    }

}