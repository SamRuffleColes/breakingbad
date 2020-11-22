package es.rufflecol.sam.breakingbad.data.repository.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @ColumnInfo(name = "character_id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "occupation") val occupation: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "series_appearances") val seriesAppearances: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(occupation)
        parcel.writeString(status)
        parcel.writeString(nickname)
        parcel.writeString(seriesAppearances)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterEntity> {
        override fun createFromParcel(parcel: Parcel): CharacterEntity {
            return CharacterEntity(parcel)
        }

        override fun newArray(size: Int): Array<CharacterEntity?> {
            return arrayOfNulls(size)
        }
    }
}