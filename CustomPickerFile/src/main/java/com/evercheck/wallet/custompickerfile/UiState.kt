package com.evercheck.wallet.custompickerfile

import android.graphics.Bitmap

sealed class UiState {
    object AddFileWidget : UiState()
    data class SummaryPDF(val nameFile: String) : UiState()
    data class SummaryPhoto(val bitmap: Bitmap) : UiState()
    data class Error(val error: String) : UiState()
}