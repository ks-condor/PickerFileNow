package com.evercheck.wallet.pickerfilenow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evercheck.wallet.pickerfilenow.models.PickerFileConfig
import com.evercheck.wallet.pickerfilenow.R

@Composable
private fun PickerSelectedPhotoView(pickerFileConfig: PickerFileConfig, modifier: Modifier = Modifier) {

}

@Composable
fun PickerSelectedPDFView(pickerFileConfig: PickerFileConfig, modifier: Modifier = Modifier, isSelectedFile: MutableState<Boolean>) {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            ImagePickerSelector(
                iconSelector = pickerFileConfig.selectedFileIcon
            )
            Spacer(modifier = Modifier.padding(bottom = 6.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = "Aqui titulo del documento.pdf",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(bottom = 26.dp))
            Divider(modifier = Modifier.alpha(.3f), color = Color.Gray, thickness = 1.5.dp)
            Spacer(modifier = Modifier.padding(bottom = 7.dp))
            ButtonRemove(isSelectedFile)
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
        Image(painter = painterResource(id = R.drawable.ic_check_circle), contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 3.dp, end = 6.dp).width(36.dp).height(36.dp))
    }
}

@Composable
private fun ButtonRemove(isSelectedFile: MutableState<Boolean>) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            isSelectedFile.value = false
        }
    ) {
        Text(text = stringResource(id = R.string.remove), color = Color.Black)
    }
}