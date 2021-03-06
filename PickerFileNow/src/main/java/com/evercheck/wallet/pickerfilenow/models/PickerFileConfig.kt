package com.evercheck.wallet.pickerfilenow.models

import com.evercheck.wallet.pickerfilenow.R

data class PickerFileConfig(
    val componentDescription: String = "Use a PDF or even a picture of your credential saved as JPG or PNG",
    val componentIcon: Int = R.drawable.ic_baseline_cloud_upload_24,
    val selectedFileIcon: Int = R.drawable.ic_file_base,
    val componentButton: ComponentButton = ComponentButton(),
    val pickerOptions:List<PickerOption>// Se debe validar si es nulo y si es vacio, si es asi tirar error
)
