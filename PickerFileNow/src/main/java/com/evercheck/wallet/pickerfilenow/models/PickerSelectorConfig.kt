package com.evercheck.wallet.pickerfilenow.models

import androidx.annotation.NonNull
import com.evercheck.wallet.pickerfilenow.R

data class PickerSelectorConfig(
    val description: String = "Use a PDF or even a picture of your credential saved as JPG or PNG",
    val iconSelector: Int = R.drawable.ic_baseline_cloud_upload_24,
    val iconSelectedFile: Int? = null,
    val iconSelectedFilePDF: Int? = null,
    val titleButtonSelector: String = "Browse",
    val colorButtonSelector: Long = 0xFF0000FF,
    @NonNull
    val options:List<PickerOption>
)
