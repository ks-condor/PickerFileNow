package com.evercheck.wallet.filehelper.helpers

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import com.evercheck.wallet.filehelper.exceptions.NotSupportedUriException
import com.evercheck.wallet.filehelper.exceptions.PathNotFoundForUriException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileHelper(
    private val context: Context,
    private val documentProvider: DocumentProvider
) : IFileHelper {

    override fun getFilePathFromUri(uri: Uri): String {
        if (documentProvider.isDocumentUri(context, uri)) {
            when (uri.authority) {
                EXTERNAL_STORAGE_AUTHORITY, DOWNLOAD_AUTHORITY, MEDIA_DOCUMENT_AUTHORITY, GOOGLE_DRIVE_AUTHORITY -> {
                    val filename = getFilenameWithoutSpacesFrom(uri)
                    if (filename != null) {
                        return getPathFromInputStreamUri(uri, filename)
                    }
                }
                GOOGLE_PHOTOS_AUTHORITY -> {
                    return uri.lastPathSegment?.let { it } ?: throw PathNotFoundForUriException()
                }
                else -> {
                    if (CONTENT_URI_SCHEME.equals(uri.scheme, ignoreCase = true)) {
                        val filename = getFilenameWithoutSpacesFrom(uri)
                        return getPathFromInputStreamUri(uri, filename)
                    }
                    if (FILE_URI_SCHEME.equals(uri.scheme, ignoreCase = true)) {
                        return uri.path?.let { it } ?: throw PathNotFoundForUriException()
                    }
                }
            }
        } else {
            return getPathFromInputStreamUri(uri)
        }

        throw NotSupportedUriException()
    }

    private fun getFilenameWithoutSpacesFrom(uri: Uri): String? {
        var filename: String? = null
        val cursor = context.contentResolver.query(
            uri, null, null, null, null
        )
        cursor?.let {
            if (it.moveToFirst()) {
                filename =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
            it.close()
        }
        return filename?.replace(SPACE, EMPTY)
    }

    override fun saveBitmapAsFile(bitmap: Bitmap): File {
        val file = createTemporalFile()
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_MAX_QUALITY, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }

    override fun createTemporalFile(
        name: String,
        fileType: String
    ): File {
        return File.createTempFile(
            name, SEPARATOR_FILE_NAME_FILE_EXTENSION + fileType.lowercase()
        )
    }

    private fun getPathFromInputStreamUri(
        uri: Uri,
        filename: String? = null
    ): String {
        var inputStream: InputStream? = null
        var filePath: String? = null

        if (uri.authority != null) {
            try {
                inputStream = context.contentResolver.openInputStream(uri)
                val photoFile = createTemporalFileFrom(inputStream, filename)

                photoFile?.let {
                    filePath = it.path
                }
            } catch (e: FileNotFoundException) {
                throw FileNotFoundException()
            } catch (e: IOException) {
                throw IOException()
            } finally {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        filePath?.let { return it }
        throw NotSupportedUriException()
    }

    private fun createTemporalFileFrom(
        inputStream: InputStream?,
        filename: String?
    ): File? {
        var targetFile: File? = null

        if (inputStream != null) {
            var read: Int
            val buffer = ByteArray(BUFFER_BYTE_ARRAY)

            targetFile = createTemporalFile(filename)
            val outputStream = FileOutputStream(targetFile)

            read = inputStream.read(buffer)
            while ((read) != -1) {
                outputStream.write(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            outputStream.flush()

            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return targetFile
    }

    private fun createTemporalFile(name: String? = null): File {
        return if (name != null) {
            File(context.cacheDir, name)
        } else {
            File(context.cacheDir, Date().time.toString() + PNG_EXTENSION)
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getDataColumn(
        contentResolver: ContentResolver,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): String {
        val column = DATA_PROJECTION
        val projection = arrayOf(column)

        val cursor = contentResolver.query(
            uri, projection,
            selection, selectionArgs, null
        )

        val dataColumn = if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            cursor.getString(index)
        } else {
            null
        }

        cursor?.close()
        dataColumn?.last { return it.toString() }
        throw NotSupportedUriException()
    }

    override fun createCroppedMediaFile(
        bitmap: ByteArray,
        file: File
    ) {
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(bitmap)
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.toString()), null
        ) { _, _ -> }
        fileOutputStream.close()
    }

    @Throws(IOException::class, SecurityException::class)
    override fun getOutputMediaFile(): File? {
        val appName = "data"

        if (!isExternalStorageWritable()) {
            throw SecurityException(MISSING_PERMISSIONS_EXCEPTION_MESSAGE)
        }

        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val picturesDirectory = File(storageDir, appName)

        if (!picturesDirectory.exists() && !picturesDirectory.mkdirs()) {
            throw IOException(FOLDER_NOT_FOUND_EXCEPTION)
        }

        val fileNameBuilder = StringBuilder()
        val timeStamp = SimpleDateFormat(FILE_NAME_DATE_FORMAT, Locale.US)
            .format(Date())

        val fileName = fileNameBuilder
            .append(FILE_NAME_PREFIX)
            .append(timeStamp)
            .toString()

        return File.createTempFile(
            fileName,
            JPG_EXTENSION,
            picturesDirectory
        )
    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}

private const val EMPTY = ""
private const val SPACE = " "
private const val IMAGE_MAX_QUALITY = 100
private const val BUFFER_BYTE_ARRAY = 8 * 1024
private const val CONTENT_URI_SCHEME = "content"
private const val DATA_PROJECTION = "_data"
private const val FILE_URI_SCHEME = "file"
private const val DOWNLOAD_AUTHORITY = "com.android.providers.downloads.documents"
private const val EXTERNAL_STORAGE_AUTHORITY = "com.android.externalstorage.documents"
private const val MEDIA_DOCUMENT_AUTHORITY = "com.android.providers.media.documents"
private const val GOOGLE_PHOTOS_AUTHORITY = "com.google.android.apps.photos.content"
private const val GOOGLE_DRIVE_AUTHORITY = "com.google.android.apps.docs.storage"
private const val FILE_NAME_DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss"
private const val FILE_NAME_PREFIX = "Certification_"
private const val FOLDER_NOT_FOUND_EXCEPTION = "missing folder to save file or failed to find folder"
private const val JPG_EXTENSION = ".jpg"
private const val MISSING_PERMISSIONS_EXCEPTION_MESSAGE = "Missing permisions to do this action"
private const val PNG_EXTENSION = ".png"
private const val SEPARATOR_FILE_NAME_FILE_EXTENSION = "."
