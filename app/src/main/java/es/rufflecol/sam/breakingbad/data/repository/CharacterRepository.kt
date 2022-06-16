package es.rufflecol.sam.breakingbad.data.repository

import androidx.lifecycle.LiveData
import es.rufflecol.sam.breakingbad.data.api.BreakingBadApi
import es.rufflecol.sam.breakingbad.data.api.dto.asEntities
import es.rufflecol.sam.breakingbad.data.repository.dao.CharacterDao
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


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

class ApiCharactersRepository @Inject constructor(
    private val api: BreakingBadApi,
    private val characterDao: CharacterDao
) :
    CharactersRepository {

    override val allCharacters = characterDao.getAll()

    override fun filterBySeries(series: String): Flow<List<CharacterEntity>> =
        characterDao.filterBySeries(series)

    override fun searchByNameAndFilterBySeries(
        query: String,
        series: String
    ): Flow<List<CharacterEntity>> = characterDao.searchByNameAndFilterBySeries(query, series)

    override fun searchByName(query: String): Flow<List<CharacterEntity>> =
        characterDao.searchByName(query)

    override suspend fun update() {
        with(api.fetchAllCharacters()) {
            if (isSuccessful) {
                val characters = body()?.asEntities()
                characterDao.insertAll(characters.orEmpty())
            }
        }
    }

}