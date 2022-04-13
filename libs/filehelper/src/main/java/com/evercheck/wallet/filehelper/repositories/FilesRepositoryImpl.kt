package com.evercheck.wallet.filehelper.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.evercheck.wallet.filehelper.helpers.IFileHelper
import java.io.File

/*
 * Created by Kevin Serrano on 11/04/22
 * Condor Labs
 * kserrano@condorlabs.io
 */
class FilesRepositoryImpl(private val fileHelper: IFileHelper) : FilesRepository {

    override fun getFilePathFromUri(uri: Uri): String {
        return fileHelper.getFilePathFromUri(uri)
    }

    override fun saveBitmapAsFile(bitmap: Bitmap): File {
        return fileHelper.saveBitmapAsFile(bitmap)
    }

    override fun getTemporalFile(
        name: String,
        fileType: String
    ): File {
        return fileHelper.createTemporalFile(name, fileType)
    }
}
