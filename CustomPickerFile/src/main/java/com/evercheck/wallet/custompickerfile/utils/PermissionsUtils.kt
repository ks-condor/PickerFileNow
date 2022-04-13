package com.evercheck.wallet.custompickerfile.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.evercheck.wallet.custompickerfile.*

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun PermissionRequest(viewModel: PickerComponentViewModel) {
    val permissionState = remember { viewModel.permissionState }
    if (permissionState.value == PermissionState.Request) {
        val context = LocalContext.current
        if (!hasPermissions(context)) {
            viewModel.setPermissionState(PermissionState.PermissionNotGranted)
            ActivityCompat.requestPermissions(
                context as Activity,
                PERMISSIONS_REQUIRED,
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            viewModel.setPermissionState(PermissionState.HasPermission)
        }
        /*
        val permissionState =
            rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionState.shouldShowRationale || !permissionState.permissionRequested) {
            viewModel.setPermissionState(PermissionState.PermissionNotGranted)
            permissionState.launchPermissionRequest()
        } else {
            viewModel.setPermissionState(PermissionState.HasPermission)
        }
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = { viewModel.setPermissionState(PermissionState.PermissionNotGranted) },
            permissionNotAvailableContent = { viewModel.setPermissionState(PermissionState.PermissionNotGranted) },
            content = { viewModel.setPermissionState(PermissionState.HasPermission) })
        */
    }
}

private fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)