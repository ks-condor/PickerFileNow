package com.evercheck.wallet.custompickerfile

sealed class PermissionState {
    object Request : PermissionState()
    object HasPermission : PermissionState()
    object PermissionNotGranted : PermissionState()
}