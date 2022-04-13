package com.evercheck.wallet.custompickerfile.models

sealed class TypeOption {
    object Photo : TypeOption()
    object PDF : TypeOption()
}
