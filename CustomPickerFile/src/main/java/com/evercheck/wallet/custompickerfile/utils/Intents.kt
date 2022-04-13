package com.evercheck.wallet.custompickerfile.utils

import android.content.Intent
import android.provider.MediaStore


fun createIntentOpenGalleryToUpload(): Intent = Intent(
    Intent.ACTION_PICK,
    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
).apply {
    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
}

fun createIntentOpenGalleryToUploadPhotoOnly(): Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

fun createIntentFileToUploadPdfOnly():Intent = Intent().apply {
    type = ANY_FILE_INTENT_TYPE
    action = Intent.ACTION_GET_CONTENT
    putExtra(
        Intent.EXTRA_MIME_TYPES,
        arrayOf(
            PDF_FILE_INTENT_TYPE
        )
    )
}

private const val ANY_FILE_INTENT_TYPE = "*/*"
private const val PDF_FILE_INTENT_TYPE = "application/pdf"