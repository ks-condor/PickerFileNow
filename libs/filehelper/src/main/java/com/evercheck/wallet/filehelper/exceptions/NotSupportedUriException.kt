package com.evercheck.wallet.filehelper.exceptions

class NotSupportedUriException : Exception() {
    override val message: String
        get() = NOT_SUPPORTED_URI_EXCEPTION
}

private const val NOT_SUPPORTED_URI_EXCEPTION = "NOT_SUPPORTED_URI_EXCEPTION"
