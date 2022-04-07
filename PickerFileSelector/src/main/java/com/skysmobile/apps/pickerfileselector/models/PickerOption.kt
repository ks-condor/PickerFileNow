package com.skysmobile.apps.pickerfileselector.models

data class PickerOption(
    val name: String = "",
    val icon: Int? = null,
    val colorIcon: Long = 0xFF0000FF,
    val requestCode: Int = 0
)
