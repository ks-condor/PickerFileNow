package com.evercheck.wallet.custompickerfile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evercheck.wallet.custompickerfile.models.FileSelectedData
import com.evercheck.wallet.custompickerfile.models.PickerFileConfig
import com.evercheck.wallet.custompickerfile.ui.PickerFileListener
import com.evercheck.wallet.filehelper.helpers.DocumentProviderImpl
import com.evercheck.wallet.filehelper.helpers.FileHelper
import com.evercheck.wallet.filehelper.interactors.GetChosenFileInteractor
import com.evercheck.wallet.filehelper.models.GetFileParams
import com.evercheck.wallet.filehelper.repositories.FilesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PickerComponentViewModel @Inject constructor(
    /*private val getChosenFileInteractor: GetChosenFileInteractor*/
): ViewModel() {

    private var context:Context? = null

    private val getChosenFileInteractor: GetChosenFileInteractor by lazy {
        GetChosenFileInteractor(fileRepository = FilesRepositoryImpl(
            fileHelper = FileHelper(context = context!!, documentProvider = DocumentProviderImpl())))
    }

    private var pickerFileListener: PickerFileListener? = null
    lateinit var pickerFileConfig: PickerFileConfig
    var fileSelectedData = FileSelectedData()
    private val _uiState = mutableStateOf<UiState>(UiState.AddFileWidget)
    val uiState: State<UiState> get() = _uiState
    private val _permissionState = mutableStateOf<PermissionState>(PermissionState.PermissionNotGranted)
    val permissionState: State<PermissionState> get() = _permissionState

    fun setAppContext(context: Context){
        this.context = context
    }

    fun addPickerFileConfig(pickerFileConfig: PickerFileConfig) {
        this.pickerFileConfig = pickerFileConfig
    }

    fun setPickerFileListener(pickerFileListener: PickerFileListener) {
        this.pickerFileListener = pickerFileListener
    }

    fun setStatus(state: UiState) {
        _uiState.value = state
    }

    fun setPermissionState(state: PermissionState) {
        _permissionState.value = state
    }

    fun onPhotoSelected(context: Context, requestCode:Int, uri: Uri) {
        viewModelScope.launch {
            try {
                val chosenFile = withContext(Dispatchers.IO) {
                    getChosenFileInteractor(GetFileParams(uri = uri))
                }
                fileSelectedData = fileSelectedData.copy(requestCode = requestCode,
                    nameFile = chosenFile.fileName,
                    filePathString = chosenFile.filePathString)
                fileSelectedData = fileSelectedData.copy(requestCode = requestCode,
                    nameFile = chosenFile.fileName,
                    filePathString = chosenFile.filePathString)
                onResultSelectedFile(componentId = pickerFileConfig.componentId)
                _uiState.value = UiState.SummaryPhoto(bitmap = getBitmapFromUri(context = context, uri = uri))
            } catch (e: Exception) {
                e.message?.let {
                    _uiState.value = UiState.Error(it)
                }
            }
        }
    }

    private fun getBitmapFromUri(context: Context, uri: Uri) : Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }

    fun onDocumentSelected(requestCode:Int, uri: Uri) {
        viewModelScope.launch {
            try {
                val chosenFile = withContext(Dispatchers.IO) {
                    getChosenFileInteractor(GetFileParams(uri = uri))
                }
                fileSelectedData = fileSelectedData.copy(requestCode = requestCode,
                    nameFile = chosenFile.fileName,
                    filePathString = chosenFile.filePathString)
                onResultSelectedFile(componentId = pickerFileConfig.componentId)
                _uiState.value = UiState.SummaryPDF(nameFile = chosenFile.fileName)
            } catch (e: Exception) {
                e.message?.let {
                    _uiState.value = UiState.Error(it)
                }
            }
        }
    }

    private fun onResultSelectedFile(componentId: Int) {
        pickerFileListener?.onResultSelectedFile(componentId = componentId,
            requestCode = fileSelectedData.requestCode,
            filePathString = fileSelectedData.filePathString)
    }

    fun onRemovedFile() {
        pickerFileListener?.onRemovedFile(componentId = pickerFileConfig.componentId, requestCode = fileSelectedData.requestCode)
    }
}