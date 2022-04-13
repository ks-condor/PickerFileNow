package com.evercheck.wallet.filehelper.helpers

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

/*
* Created by Kevin Serrano on 11/04/22
* Condor Labs
* kserrano@condorlabs.io
*/
interface IFileHelper {

    fun getFilePathFromUri(uri: Uri): String

    fun createCroppedMediaFile(
        bitmap: ByteArray,
        file: File
    )

    fun getOutputMediaFile(): File?

    fun saveBitmapAsFile(bitmap: Bitmap): File

    fun createTemporalFile(
        name: String,
        fileType: String
    ): File
}