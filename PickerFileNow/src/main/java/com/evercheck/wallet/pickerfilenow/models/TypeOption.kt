package com.evercheck.wallet.pickerfilenow.models

sealed class TypeOption {
    object File : TypeOption()
    object Gallery : TypeOption()
    object Photo : TypeOption()
    object PDF : TypeOption()
}
