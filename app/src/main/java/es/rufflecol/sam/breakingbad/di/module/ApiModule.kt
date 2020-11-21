package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import es.rufflecol.sam.breakingbad.data.api.BreakingBadApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiClientt(): BreakingBadApi = Retrofit.Builder()
        .baseUrl("https://breakingbadapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BreakingBadApi::class.java)

}