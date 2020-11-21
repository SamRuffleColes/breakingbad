package es.rufflecol.sam.breakingbad.data.api.dto

import com.google.gson.annotations.SerializedName
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity

data class BreakingBadCharacterDto(
    @SerializedName("char_id") val charId: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("occupation") val occupations: List<String>?,
    @SerializedName("img") val image: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("appearance") val appearances: List<Int>?,
    @SerializedName("portrayed") val portrayedBy: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("better_call_saul_appearance") val betterCallSaulAppearances: List<Int>?
)

fun List<BreakingBadCharacterDto>?.asEntities(): List<CharacterEntity> {
    return this?.map {

        val bb = it.appearances.orEmpty().map { series -> "BB$series" }
        val bcs = it.betterCallSaulAppearances.orEmpty().map { series -> "BCS$series" }

        val appearances = listOf(bb, bcs).flatten().joinToString(separator = ",")

        CharacterEntity(
            id = it.charId,
            name = it.name.orEmpty(),
            image = it.image.orEmpty(),
            occupation = it.occupations.orEmpty().joinToString(separator = ", "),
            status = it.status.orEmpty(),
            nickname = it.nickname.orEmpty(),
            seriesAppearances = appearances
        )
    } ?: emptyList()
}