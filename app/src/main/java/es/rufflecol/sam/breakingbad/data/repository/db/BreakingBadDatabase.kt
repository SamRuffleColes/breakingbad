package es.rufflecol.sam.breakingbad.data.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import es.rufflecol.sam.breakingbad.data.repository.dao.CharacterDao
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class BreakingBadDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}