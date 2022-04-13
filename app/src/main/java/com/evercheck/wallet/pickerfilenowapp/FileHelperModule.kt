package com.evercheck.wallet.pickerfilenowapp

/*
import android.content.Context
import com.skysmobile.apps.filehelper.helpers.DocumentProvider
import com.skysmobile.apps.filehelper.helpers.DocumentProviderImpl
import com.skysmobile.apps.filehelper.helpers.FileHelper
import com.skysmobile.apps.filehelper.helpers.IFileHelper
import com.skysmobile.apps.filehelper.repositories.FilesRepository
import com.skysmobile.apps.filehelper.repositories.FilesRepositoryImpl
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
*/
