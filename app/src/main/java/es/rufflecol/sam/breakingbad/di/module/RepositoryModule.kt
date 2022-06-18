package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import es.rufflecol.sam.breakingbad.characters.data.remote.BreakingBadApi
import es.rufflecol.sam.breakingbad.characters.data.repository.CharacterRepositoryImpl
import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.characters.data.source.BreakingBadDatabase

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCharactersRepository(
        api: BreakingBadApi,
        database: BreakingBadDatabase
    ): CharactersRepository = CharacterRepositoryImpl(api, database.characterDao)

}