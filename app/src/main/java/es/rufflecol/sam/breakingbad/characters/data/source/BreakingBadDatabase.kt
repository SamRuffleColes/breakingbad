package es.rufflecol.sam.breakingbad.characters.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class BreakingBadDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}