package es.rufflecol.sam.breakingbad.characters.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%'")
    fun searchByName(query: String): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE series_appearances LIKE '%' || :series || '%'")
    fun filterBySeries(series: String): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%' AND series_appearances LIKE '%' || :series || '%'")
    fun searchByNameAndFilterBySeries(query: String, series: String): Flow<List<CharacterEntity>>


}