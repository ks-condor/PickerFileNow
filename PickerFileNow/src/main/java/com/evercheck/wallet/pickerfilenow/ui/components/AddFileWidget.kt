package com.evercheck.wallet.pickerfilenow.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.evercheck.wallet.pickerfilenow.models.PickerFileConfig
import com.evercheck.wallet.pickerfilenow.models.PickerOption

@Composable
fun AddFileWidget(pickerFileConfig: PickerFileConfig, modifier: Modifier = Modifier, isSelectedFile: MutableState<Boolean>) {
    val showDialog = remember { mutableStateOf(false) }
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
        ButtonBrowse(pickerFileConfig, showDialog)
    }
    CustomDialog(options = pickerFileConfig.pickerOptions, showDialog = showDialog, isSelectedFile = isSelectedFile)
}

@Composable
fun ImagePickerSelector(iconSelector: Int) {
    Image(painter = painterResource(id = iconSelector), contentDescription = null)
}

@Composable
fun ButtonBrowse(pickerFileConfig: PickerFileConfig, showDialog:MutableState<Boolean>) {
    Button(
        onClick = {
            showDialog.value = true
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(pickerFileConfig.componentButton.componentButtonColor)
        )
    ) {
        Text(text = pickerFileConfig.componentButton.componentButtonTitle, color = Color.White)
    }
}

@Composable
private fun CustomDialog(options: List<PickerOption>, showDialog:MutableState<Boolean>, isSelectedFile: MutableState<Boolean>) {
    if (showDialog.value) {
        val context = LocalContext.current
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties()
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                    options.forEach { pickerOption ->
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))
                        Row(
                            modifier = Modifier.clickable {
                                showDialog.value = false
                                isSelectedFile.value = true
                                Toast.makeText(
                                    context,
                                    "File selected",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                }
            }
        }
    }
}

private fun drawTicketPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()
        // Top left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = -cornerRadius,
                right = cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width - cornerRadius, y = 0f)
        // Top right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = -cornerRadius,
                right = size.width + cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width, y = size.height - cornerRadius)
        // Bottom right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = size.height - cornerRadius,
                right = size.width + cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = cornerRadius, y = size.height)
        // Bottom left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = size.height - cornerRadius,
                right = cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = 0f, y = cornerRadius)
        close()
    }
}

/*
Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .drawBehind {
                scale(scale = 0.9f) {
                    drawPath(
                        path = drawTicketPath(size = size, cornerRadius = 2.dp.toPx()),
                        color = Color.Gray,
                        alpha = .3f,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    )
                }
            }
            .padding(32.dp)
* */