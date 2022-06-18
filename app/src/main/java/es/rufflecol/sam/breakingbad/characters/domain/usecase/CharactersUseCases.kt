package es.rufflecol.sam.breakingbad.characters.domain.usecase

data class CharactersUseCases(
    val updateCharacters: UpdateCharactersUseCase,
    val getCharacters: GetCharactersUseCase,
    val getSeries: GetSeriesUseCase
)