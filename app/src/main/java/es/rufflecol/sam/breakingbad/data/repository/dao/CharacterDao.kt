package es.rufflecol.sam.breakingbad.data.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getAll(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%'")
    fun searchByName(query: String): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE series_appearances LIKE '%' || :series || '%'")
    fun filterBySeries(series: String): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%' AND series_appearances LIKE '%' || :series || '%'")
    fun searchByNameAndFilterBySeries(query: String, series: String): LiveData<List<CharacterEntity>>


}