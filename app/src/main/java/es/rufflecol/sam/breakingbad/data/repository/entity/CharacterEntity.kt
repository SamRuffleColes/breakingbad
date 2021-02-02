package es.rufflecol.sam.breakingbad.data.repository.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "characters")
@Parcelize
data class CharacterEntity(
    @ColumnInfo(name = "character_id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "occupation") val occupation: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "series_appearances") val seriesAppearances: String,
) : Parcelable