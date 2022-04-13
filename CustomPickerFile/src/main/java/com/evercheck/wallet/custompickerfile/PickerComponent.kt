package com.evercheck.wallet.custompickerfile

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.evercheck.wallet.custompickerfile.models.PickerFileConfig
import com.evercheck.wallet.custompickerfile.ui.PickerFileListener
import com.evercheck.wallet.custompickerfile.ui.components.AddFileWidget
import com.evercheck.wallet.custompickerfile.ui.components.SummaryPDF
import com.evercheck.wallet.custompickerfile.ui.components.SummaryPhoto
import com.evercheck.wallet.custompickerfile.ui.createDrawPath
import com.evercheck.wallet.custompickerfile.utils.PermissionRequest

class PickerComponent(
    private val pickerFileConfig: PickerFileConfig,
    private val pickerFileListener: PickerFileListener
): ComponentActivity() {

    @Composable
    fun ContentScreen() {
        val context = LocalContext.current
        val viewModel: PickerComponentViewModel = viewModel(key = pickerFileConfig.componentId.toString())
        viewModel.setAppContext(context = context)
        ObserveStates(viewModel)
        PermissionRequest(viewModel = viewModel)
        viewModel.addPickerFileConfig(pickerFileConfig = pickerFileConfig)
        viewModel.setPickerFileListener(pickerFileListener = pickerFileListener)
    }

    @Composable
    private fun ObserveStates(viewModel: PickerComponentViewModel) {

        val uiState = remember { viewModel.uiState }.value

        when (uiState) {
            is UiState.AddFileWidget -> {
                AddFileWidget(viewModel = viewModel, pickerFileConfig = pickerFileConfig, modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .drawBehind {
                        scale(scale = 0.9f) {
                            drawPath(
                                path = createDrawPath(size = size, cornerRadius = 2.dp.toPx()),
                                color = Color.Gray,
                                alpha = .3f,
                                style = Stroke(
                                    width = 2.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(
                                            10f,
                                            10f
                                        )
                                    )
                                )
                            )
                        }
                    }
                    .padding(32.dp)
                )
            }
            is UiState.SummaryPhoto -> {
                SummaryPhoto(
                    bitmap = uiState.bitmap, modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                        .background(color = Color.White), viewModel = viewModel
                )
            }
            is UiState.SummaryPDF -> {
                SummaryPDF(
                    nameFile = uiState.nameFile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                        .background(color = Color.White),
                    viewModel = viewModel
                )
            }
            is UiState.Error -> {
                viewModel.setStatus(UiState.AddFileWidget)
                val context = LocalContext.current
                Toast.makeText(
                    context, uiState.error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}