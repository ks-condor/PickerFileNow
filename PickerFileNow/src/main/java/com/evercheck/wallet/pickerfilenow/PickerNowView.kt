package com.evercheck.wallet.pickerfilenow

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.evercheck.wallet.pickerfilenow.models.PickerFileConfig
import com.evercheck.wallet.pickerfilenow.ui.theme.PickerFileSelecterTestTheme

class PickerNowView(private val pickerFileConfig: PickerFileConfig) {

    private val isSelectedFile = mutableStateOf(false)

    @Composable
    fun ShowContentView() {
        if (isSelectedFile.value) {
            PickerSelectedPDFView()
        } else {
            PickerView()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PickerFileSelecterTestTheme {
        PickerNowView(PickerFileConfig(pickerOptions = listOf())).PickerSelectedPDFView()
    }
}