package com.evercheck.wallet.custompickerfile.ui.components

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evercheck.wallet.custompickerfile.PickerComponentViewModel
import com.evercheck.wallet.custompickerfile.R
import com.evercheck.wallet.custompickerfile.UiState

@Composable
fun SummaryPhoto(
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
    viewModel: PickerComponentViewModel
) {
    Box() {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            SummaryImagePreview(bitmap = bitmap)
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            Divider(modifier = Modifier.alpha(.3f), color = Color.Gray, thickness = 1.5.dp)
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            ButtonRemove(viewModel = viewModel)
            Spacer(modifier = Modifier.padding(bottom = 7.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.ic_check_circle), contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 6.dp)
                .width(36.dp)
                .height(36.dp)
        )
    }
}

@Composable
fun SummaryPDF(
    nameFile: String,
    modifier: Modifier = Modifier,
    viewModel: PickerComponentViewModel
) {
    Box() {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            ImagePickerSelector(
                iconSelector = viewModel.pickerFileConfig.selectedFileIcon
            )
            Spacer(modifier = Modifier.padding(bottom = 6.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = nameFile,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(bottom = 26.dp))
            Divider(modifier = Modifier.alpha(.3f), color = Color.Gray, thickness = 1.5.dp)
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            ButtonRemove(viewModel = viewModel)
            Spacer(modifier = Modifier.padding(bottom = 7.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.ic_check_circle), contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 6.dp)
                .width(36.dp)
                .height(36.dp)
        )
    }
}

@Composable
fun SummaryImagePreview(bitmap: Bitmap) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Preview",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(130.dp)
    )
}

@Composable
private fun ButtonRemove(viewModel: PickerComponentViewModel) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            viewModel.onRemovedFile()
            viewModel.setStatus(UiState.AddFileWidget)
        }
    ) {
        Text(text = stringResource(id = R.string.remove), color = Color.Black)
    }
}