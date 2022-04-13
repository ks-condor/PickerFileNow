package com.evercheck.wallet.custompickerfile.di

import android.content.Context
import com.evercheck.wallet.filehelper.helpers.DocumentProvider
import com.evercheck.wallet.filehelper.helpers.DocumentProviderImpl
import com.evercheck.wallet.filehelper.helpers.FileHelper
import com.evercheck.wallet.filehelper.helpers.IFileHelper
import com.evercheck.wallet.filehelper.repositories.FilesRepository
import com.evercheck.wallet.filehelper.repositories.FilesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HelperModule {

    @Provides
    fun providerDocumentProvider(): DocumentProvider {
        return DocumentProviderImpl()
    }

    @Provides
    fun provideFileHelper(
        @ApplicationContext context: Context,
        documentProvider: DocumentProvider
    ): IFileHelper = FileHelper(context, documentProvider)

    @Provides
    fun provideFileRepository(fileHelper: IFileHelper): FilesRepository {
        return FilesRepositoryImpl(fileHelper)
    }
}

