package es.rufflecol.sam.breakingbad

import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity

fun testCharacter(
    id: Int = 0,
    name: String = "",
    image: String = "",
    occupation: String = "",
    status: String = "",
    nickname: String = "",
    seriesAppearances: String = ""
) = CharacterEntity(
    id = id,
    name = name,
    image = image,
    occupation = occupation,
    status = status,
    nickname = nickname,
    seriesAppearances = seriesAppearances
)