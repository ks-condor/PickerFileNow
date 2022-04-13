package com.evercheck.wallet.custompickerfile.ui.components

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.evercheck.wallet.custompickerfile.PermissionState
import com.evercheck.wallet.custompickerfile.PickerComponentViewModel
import com.evercheck.wallet.custompickerfile.models.PickerFileConfig
import com.evercheck.wallet.custompickerfile.models.PickerOption
import com.evercheck.wallet.custompickerfile.models.TypeOption
import com.evercheck.wallet.custompickerfile.utils.createIntentFileToUploadPdfOnly
import com.evercheck.wallet.custompickerfile.utils.createIntentOpenGalleryToUpload
import com.evercheck.wallet.custompickerfile.utils.createIntentOpenGalleryToUploadPhotoOnly

@Composable
fun AddFileWidget(
    viewModel: PickerComponentViewModel,
    pickerFileConfig: PickerFileConfig,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImagePickerSelector(iconSelector = pickerFileConfig.componentIcon)
        Spacer(modifier = Modifier.padding(bottom = 14.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = pickerFileConfig.componentDescription,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(bottom = 7.dp))
        ButtonBrowse(viewModel = viewModel, pickerFileConfig)
    }
}

@Composable
fun ImagePickerSelector(iconSelector: Int) {
    Image(painter = painterResource(id = iconSelector), contentDescription = null)
}

@Composable
fun ButtonBrowse(viewModel: PickerComponentViewModel, pickerFileConfig: PickerFileConfig) {
    val showDialog = remember { mutableStateOf(false) }
    Button(
        onClick = {
            if (viewModel.permissionState.value == PermissionState.HasPermission) {
                showDialog.value = true
            } else {
                viewModel.setPermissionState(PermissionState.Request)
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(pickerFileConfig.componentButton.componentButtonColor)
        )
    ) {
        Text(text = pickerFileConfig.componentButton.componentButtonTitle, color = Color.White)
    }
    if (showDialog.value) {
        val context = LocalContext.current
        CustomDialog(
            options = pickerFileConfig.pickerOptions,
            onSelectedFile = { requestCode, typeOption, uri ->
                showDialog.value = false
                when (typeOption) {
                    TypeOption.Photo -> {
                        viewModel.onPhotoSelected(context = context, requestCode = requestCode, uri = uri)
                    }
                    TypeOption.PDF -> {
                        viewModel.onDocumentSelected(requestCode = requestCode, uri = uri)
                    }
                }
            },
            onDismiss = {
                showDialog.value = false
            })
    }
}

@Composable
private fun CustomDialog(
    options: List<PickerOption>,
    onSelectedFile: (requestCode: Int, typeOption: TypeOption, uri: Uri) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss.invoke() },
        properties = DialogProperties()
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            OptionItems(pickerOptions = options, onSelectedFile = onSelectedFile)
        }
    }
}

@Composable
private fun OptionItems(
    pickerOptions: List<PickerOption>,
    onSelectedFile: (requestCode: Int, typeOption: TypeOption, uri: Uri) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
        items(pickerOptions) { option: PickerOption ->
            OptionItem(pickerOption = option, onSelectedFile = onSelectedFile)
        }
    }
}

@Composable
fun OptionItem(
    pickerOption: PickerOption,
    onSelectedFile: (requestCode: Int, typeOption: TypeOption, uri: Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode != Activity.RESULT_OK) {
            return@rememberLauncherForActivityResult
        }
        activityResult.data?.data?.let { uri ->
            onSelectedFile.invoke(pickerOption.requestCode, pickerOption.typeOption, uri)
        }
    }
    Spacer(modifier = Modifier.padding(bottom = 10.dp))
    Row(
        modifier = Modifier.clickable {
            launcher.launch(
                when (pickerOption.typeOption) {
                    TypeOption.Photo -> createIntentOpenGalleryToUploadPhotoOnly()
                    else -> createIntentFileToUploadPdfOnly()
                }
            )
            //onClickItem.invoke(pickerOption.requestCode, pickerOption.typeOption)
            //isSelectedFile.value = true
        }
    ) {
        Text(
            pickerOption.name,
            modifier = Modifier
                .weight(4F)
                .padding(start = 20.dp, end = 20.dp),
            textAlign = TextAlign.Start
        )
        pickerOption.icon?.let { icon ->
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.weight(1F),
                tint = Color(pickerOption.colorIcon)
            )
        }
    }
    Spacer(modifier = Modifier.padding(bottom = 10.dp))
}

