package com.evercheck.wallet.filehelper.helpers

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract

class DocumentProviderImpl : DocumentProvider {

    override fun isDocumentUri(
        context: Context,
        uri: Uri
    ): Boolean {
        return DocumentsContract.isDocumentUri(context, uri)
    }

    override fun getDocumentId(uri: Uri): String {
        return DocumentsContract.getDocumentId(uri)
    }
}
