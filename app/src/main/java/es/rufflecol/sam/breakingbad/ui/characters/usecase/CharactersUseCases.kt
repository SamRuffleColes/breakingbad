package es.rufflecol.sam.breakingbad.ui.characters.usecase

data class CharactersUseCases(
    val updateCharacters: UpdateCharactersUseCase,
    val getCharacters: GetCharactersUseCase,
    val getSeries: GetSeriesUseCase
)