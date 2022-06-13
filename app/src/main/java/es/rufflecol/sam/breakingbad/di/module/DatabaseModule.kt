package es.rufflecol.sam.breakingbad.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.rufflecol.sam.breakingbad.data.repository.dao.CharacterDao
import es.rufflecol.sam.breakingbad.data.repository.db.BreakingBadDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BreakingBadDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BreakingBadDatabase::class.java,
            "breaking_bad_database"
        ).build()
    }

    @Provides
    fun provideCharacterDao(database: BreakingBadDatabase): CharacterDao = database.characterDao

}