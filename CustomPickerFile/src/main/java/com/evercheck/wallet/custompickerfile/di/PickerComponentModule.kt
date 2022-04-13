package com.evercheck.wallet.custompickerfile.di

import com.evercheck.wallet.filehelper.interactors.GetChosenFileInteractor
import com.evercheck.wallet.filehelper.repositories.FilesRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PickerComponentModule {

    @Reusable
    @Provides
    fun provideGetChosenFileInteractor(
        fileRepository: FilesRepository
    ): GetChosenFileInteractor {
        return GetChosenFileInteractor(fileRepository)
    }
}
