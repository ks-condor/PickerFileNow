package com.evercheck.wallet.pickerfilenow.models

data class PickerOption(
    val name: String = "",
    val icon: Int? = null,
    val colorIcon: Long = 0xFF0000FF,
    val typeOption: TypeOption,
    val requestCode: Int = 0
)
