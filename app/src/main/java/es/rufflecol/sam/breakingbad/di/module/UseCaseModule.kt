package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.ui.characters.usecase.CharactersUseCases
import es.rufflecol.sam.breakingbad.ui.characters.usecase.GetCharactersUseCase
import es.rufflecol.sam.breakingbad.ui.characters.usecase.GetSeriesUseCase
import es.rufflecol.sam.breakingbad.ui.characters.usecase.UpdateCharactersUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideCharactersUseCases(repository: CharactersRepository): CharactersUseCases =
        CharactersUseCases(
            updateCharacters = UpdateCharactersUseCase(repository),
            getCharacters = GetCharactersUseCase(repository),
            getSeries = GetSeriesUseCase(repository)
        )

}