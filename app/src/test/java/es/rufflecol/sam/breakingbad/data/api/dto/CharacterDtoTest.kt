package es.rufflecol.sam.breakingbad.data.api.dto

import es.rufflecol.sam.breakingbad.characters.data.remote.dto.BreakingBadCharacterDto
import es.rufflecol.sam.breakingbad.characters.data.remote.dto.asEntities
import es.rufflecol.sam.breakingbad.characters.domain.model.CharacterEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CharacterDtoTest {

    @Test
    fun `conversion to entities handles null inputs`() {
        val dto = BreakingBadCharacterDto(
            charId = 0,
            name = null,
            birthday = null,
            occupations = null,
            image = null,
            status = null,
            nickname = null,
            appearances = null,
            portrayedBy = null,
            category = null,
            betterCallSaulAppearances = null
        )

        val entities = listOf(dto).asEntities()

        assertThat(entities).first().isEqualTo(
            CharacterEntity(
                id = 0,
                name = "",
                image = "",
                occupation = "",
                status = "",
                nickname = "",
                seriesAppearances = ""
            )
        )
    }

    @Test
    fun `conversion to entities parses lists to csv`() {
        val dto = BreakingBadCharacterDto(
            charId = 1,
            name = "name",
            birthday = "01-01-2020",
            occupations = listOf("1", "2", "3"),
            image = "http://img.com",
            status = "status",
            nickname = "nickname",
            appearances = listOf(1,2,4),
            portrayedBy = "portrayedBy",
            category = "category",
            betterCallSaulAppearances = listOf(3,4)
        )

        val entities = listOf(dto).asEntities()

        assertThat(entities).first().isEqualTo(
            CharacterEntity(
                id = 1,
                name = "name",
                image = "http://img.com",
                occupation = "1, 2, 3",
                status = "status",
                nickname = "nickname",
                seriesAppearances = "BB1,BB2,BB4,BCS3,BCS4"
            )
        )
    }
}