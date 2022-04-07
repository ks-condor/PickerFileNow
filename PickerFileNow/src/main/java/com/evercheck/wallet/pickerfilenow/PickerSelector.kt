package com.evercheck.wallet.pickerfilenow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evercheck.wallet.pickerfilenow.models.PickerOption
import com.evercheck.wallet.pickerfilenow.models.PickerSelectorConfig
import com.evercheck.wallet.pickerfilenow.ui.theme.PickerFileSelecterTestTheme

class PickerSelector(private val pickerSelectorConfig: PickerSelectorConfig) {

    private val showDialog = mutableStateOf(false)

    @Composable
    fun PickerSelectorView() {
        Column(
            modifier = Modifier
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
                .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            ImagePickerSelector(iconSelector = pickerSelectorConfig.iconSelector)
            Spacer(modifier = Modifier.padding(bottom = 14.dp))
            Text(text = pickerSelectorConfig.description, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.padding(bottom = 7.dp))
            ButtonBrowse(pickerSelectorConfig.colorButtonSelector)
        }
    }

    @Composable
    fun PickerSelectedView() {

    }

    @Composable
    private fun ImagePickerSelector(iconSelector: Int) {
        Image(painter = painterResource(id = iconSelector), contentDescription = null)
    }

    @Composable
    private fun ButtonBrowse(backgroundColor: Long) {
        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }
        Button(
            onClick = { expanded = true },
            /*
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            */
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(backgroundColor))
        ) {
            Text(text = pickerSelectorConfig.titleButtonSelector , color = Color.White)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                pickerSelectorConfig.options.forEach { pickerOption ->
                    DropdownMenuItem(onClick = { expanded = false
                        Toast.makeText(context, "Click --> ${pickerOption.name}", Toast.LENGTH_SHORT).show()
                    }) {
                        Row(
                            modifier = Modifier.fillMaxWidth()) {
                            Text(pickerOption.name, modifier = Modifier.weight(4F).padding(end = 20.dp),
                                textAlign = TextAlign.Start)
                            pickerOption.icon?.let { icon ->
                                Icon(painter = painterResource(id = icon), contentDescription = null,
                                    modifier = Modifier.weight(1F), tint = Color(pickerOption.colorIcon))
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DropdownMenuOption(pickerOption: PickerOption) {
        DropdownMenuItem(onClick = {  }) {
            Box() {
                Text(pickerOption.name, modifier = Modifier.padding(end = 60.dp),
                    textAlign = TextAlign.Center)
                pickerOption.icon?.let { icon ->
                    Icon(painter = painterResource(id = icon), contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd))
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PickerFileSelecterTestTheme {
        PickerSelector(PickerSelectorConfig(options = listOf())).PickerSelectorView()
    }
}