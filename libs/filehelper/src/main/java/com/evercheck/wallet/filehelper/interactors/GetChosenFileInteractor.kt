package com.evercheck.wallet.filehelper.interactors

import com.evercheck.wallet.filehelper.models.ChosenFile
import com.evercheck.wallet.filehelper.models.GetFileParams
import com.evercheck.wallet.filehelper.repositories.FilesRepository
import java.io.File

/*
 * Created by Kevin Serrano on 11/04/22
 * Condor Labs
 * kserrano@condorlabs.io
 */
class GetChosenFileInteractor(
    private val fileRepository: FilesRepository
) {

    operator fun invoke(params: GetFileParams): ChosenFile {
        val filepath = fileRepository.getFilePathFromUri(params.uri)
        val file = File(filepath)
        return ChosenFile(filepath, file.name)
    }
}
