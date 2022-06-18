package es.rufflecol.sam.breakingbad.characters.domain.usecase

import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(private val repository: CharactersRepository) {

    operator fun invoke(
        nameQuery: String = "",
        seriesFilter: String = ""
    ): Flow<List<CharacterEntity>> {
        return when {
            seriesFilter.isBlank() && nameQuery.isBlank() -> repository.allCharacters
            nameQuery.isBlank() -> repository.filterBySeries(seriesFilter)
            seriesFilter.isBlank() -> repository.searchByName(nameQuery)
            else -> repository.searchByNameAndFilterBySeries(nameQuery, seriesFilter)
        }
    }
}