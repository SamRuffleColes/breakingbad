package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.rufflecol.sam.breakingbad.characters.data.remote.BreakingBadApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiClientt(): BreakingBadApi = Retrofit.Builder()
        .baseUrl("https://breakingbadapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BreakingBadApi::class.java)

}