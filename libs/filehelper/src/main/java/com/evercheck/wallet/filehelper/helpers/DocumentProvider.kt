package com.evercheck.wallet.filehelper.helpers

import android.content.Context
import android.net.Uri

interface DocumentProvider {

    fun isDocumentUri(
        context: Context,
        uri: Uri
    ): Boolean

    fun getDocumentId(uri: Uri): String
}
