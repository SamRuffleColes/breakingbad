package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import es.rufflecol.sam.breakingbad.data.api.BreakingBadApi
import es.rufflecol.sam.breakingbad.data.repository.ApiCharactersRepository
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.data.repository.db.BreakingBadDatabase

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {

    @Provides
    fun provideCharactersRepository(
        api: BreakingBadApi,
        database: BreakingBadDatabase
    ): CharactersRepository = ApiCharactersRepository(api, database.characterDao)

}