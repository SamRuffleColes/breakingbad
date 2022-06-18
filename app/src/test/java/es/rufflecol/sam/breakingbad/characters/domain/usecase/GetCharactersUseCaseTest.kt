package es.rufflecol.sam.breakingbad.characters.domain.usecase

import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.testCharacter
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    companion object {
        private const val seriesFilter = "series_filter"
        private const val nameSearchQuery = "search_query"
    }

    private val character0 = testCharacter(id = 0)
    private val character1 = testCharacter(id = 1)
    private val character2 = testCharacter(id = 2)
    private val character3 = testCharacter(id = 3)
    private val allCharactersResult = flowOf(listOf(character0, character1, character2, character3))
    private val filterBySeriesResult = flowOf(listOf(character0, character2))
    private val searchByNameResult = flowOf(listOf(character2, character3))
    private val searchByNameAndFilterBySeriesResult = flowOf(listOf(character2))

    private val repository: CharactersRepository = mockk(relaxed = true)

    private val sut = GetCharactersUseCase(repository)

    @Before
    fun setUp() {
        every { repository.allCharacters() } returns allCharactersResult
        every { repository.filterBySeries(seriesFilter) } returns filterBySeriesResult
        every { repository.searchByName(nameSearchQuery) } returns searchByNameResult
        every {
            repository.searchByNameAndFilterBySeries(
                nameSearchQuery,
                seriesFilter
            )
        } returns searchByNameAndFilterBySeriesResult
    }

    @Test
    fun `given seriesFilter is blank and nameQuery is blank, when invoke, then return all characters result`() {
        val result = sut(seriesFilter = "", nameQuery = "")

        assertThat(result)
            .isEqualTo(allCharactersResult)
    }

    @Test
    fun `given seriesFilter input and nameQuery is blank, when invoke, then return filter by series result`() {
        val result = sut(seriesFilter = seriesFilter, nameQuery = "")

        assertThat(result)
            .isEqualTo(filterBySeriesResult)
    }

    @Test
    fun `given seriesFilter is blank and nameQuery input, when invoke, then return search by name result`() {
        val result = sut(seriesFilter = "", nameQuery = nameSearchQuery)

        assertThat(result)
            .isEqualTo(searchByNameResult)
    }

    @Test
    fun `given seriesFilter is input and nameQuery is input, when invoke, then return search by name and filter by query result`() {
        val result = sut(seriesFilter = seriesFilter, nameQuery = nameSearchQuery)

        assertThat(result)
            .isEqualTo(searchByNameAndFilterBySeriesResult)
    }
}