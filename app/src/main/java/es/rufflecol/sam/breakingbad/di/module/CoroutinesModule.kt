package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.rufflecol.sam.breakingbad.common.coroutine.CoroutineDispatchProvider
import es.rufflecol.sam.breakingbad.common.coroutine.CoroutineDispatchProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    @Singleton
    fun provideCoroutinesDispatchProvider(): CoroutineDispatchProvider =
        CoroutineDispatchProviderImpl()

}