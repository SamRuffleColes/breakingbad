package es.rufflecol.sam.breakingbad.characters.data.repository

import es.rufflecol.sam.breakingbad.characters.data.source.CharacterDao
import es.rufflecol.sam.breakingbad.characters.domain.model.exception.UpdateFailedException
import es.rufflecol.sam.breakingbad.characters.data.remote.BreakingBadApi
import es.rufflecol.sam.breakingbad.characters.data.remote.dto.asEntities
import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
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

    @Throws(UpdateFailedException::class)
    override suspend fun update() {
        try {
            val response = api.fetchAllCharacters()
            if (response.isSuccessful) {
                val characters = response.body()?.asEntities()
                characterDao.insertAll(characters.orEmpty())
            } else {
                throw UpdateFailedException("api response unsuccessful")
            }
        } catch (e: Exception) {
            throw UpdateFailedException(e.message.orEmpty())
        }
    }

}