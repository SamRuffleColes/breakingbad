package es.rufflecol.sam.breakingbad.characters.domain.usecase

import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSeriesUseCase(private val repository: CharactersRepository) {

    operator fun invoke(): Flow<List<String>> {
        return repository.allCharacters.map { characters ->
            characters.map { it.seriesAppearances.split(",") }
                .flatten()
                .toSortedSet()
                .filterNot { it.isBlank() }
        }
    }
}