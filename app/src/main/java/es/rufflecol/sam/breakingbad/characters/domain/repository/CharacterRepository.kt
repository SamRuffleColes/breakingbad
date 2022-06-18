package es.rufflecol.sam.breakingbad.characters.domain.repository

import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    val allCharacters: Flow<List<CharacterEntity>>

    fun filterBySeries(series: String): Flow<List<CharacterEntity>>

    fun searchByNameAndFilterBySeries(
        query: String,
        series: String
    ): Flow<List<CharacterEntity>>

    fun searchByName(query: String): Flow<List<CharacterEntity>>

    suspend fun update()
}