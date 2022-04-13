package com.evercheck.wallet.custompickerfile.models

data class PickerOption(
    val name: String = "",
    val icon: Int? = null,
    val colorIcon: Long = 0xFF0000FF,
    val typeOption: TypeOption,
    val requestCode: Int = 0
)
