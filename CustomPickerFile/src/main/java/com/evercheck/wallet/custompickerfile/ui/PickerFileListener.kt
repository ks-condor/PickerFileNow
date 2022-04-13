package com.evercheck.wallet.custompickerfile.ui

interface PickerFileListener {

    fun onResultSelectedFile(componentId: Int, requestCode:Int, filePathString: String)

    fun onRemovedFile(componentId: Int, requestCode:Int)
}