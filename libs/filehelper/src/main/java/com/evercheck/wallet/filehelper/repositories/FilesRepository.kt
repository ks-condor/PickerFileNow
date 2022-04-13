package com.evercheck.wallet.filehelper.repositories

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

/*
 * Created by Kevin Serrano on 11/04/22
 * Condor Labs
 * kserrano@condorlabs.io
 */
interface FilesRepository {

    fun getFilePathFromUri(uri: Uri): String

    fun saveBitmapAsFile(bitmap: Bitmap): File

    fun getTemporalFile(name: String, fileType: String): File
}
