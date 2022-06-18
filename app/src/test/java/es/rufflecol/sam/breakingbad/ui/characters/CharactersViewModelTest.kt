package es.rufflecol.sam.breakingbad.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.rufflecol.sam.breakingbad.MainCoroutineRule
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.TestCoroutineDispatchProvider
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import es.rufflecol.sam.breakingbad.domain.model.exception.UpdateFailedException
import es.rufflecol.sam.breakingbad.testCharacter
import es.rufflecol.sam.breakingbad.ui.characters.usecase.CharactersUseCases
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    companion object {
        private const val nameSearchQuery = "Da"
        private const val seriesFilter = "S4"

        private val character0 = testCharacter(id = 0, name = "Tim", seriesAppearances = "S1,S2,S3,S4")
        private val character1 = testCharacter(id = 1, name = "Dan", seriesAppearances = "S1,S2,S3")
        private val character2 = testCharacter(id = 2, name = "Dave", seriesAppearances = "S1,S2,S3,S4")

        private val allCharacters = listOf(character0, character1, character2)
        private val queriedByNameCharacters = listOf(character1, character2)
        private val filteredBySeriesCharacters = listOf(character0, character2)
        private val queriedByNameAndFilteredBySeriesCharacters = listOf(character2)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private val charactersUseCases: CharactersUseCases = mockk(relaxed = true)
    private val sut = CharactersViewModel(charactersUseCases, TestCoroutineDispatchProvider())

    @Before
    fun setUp() {
        every { charactersUseCases.getCharacters() } returns flowOf(allCharacters)
        every { charactersUseCases.getCharacters(nameQuery = nameSearchQuery) } returns flowOf(
            queriedByNameCharacters
        )
        every { charactersUseCases.getCharacters(seriesFilter = seriesFilter) } returns flowOf(
            filteredBySeriesCharacters
        )
        every {
            charactersUseCases.getCharacters(
                nameQuery = nameSearchQuery,
                seriesFilter = seriesFilter
            )
        } returns flowOf(
            queriedByNameAndFilteredBySeriesCharacters
        )

        sut.characters.observeForever {}
    }

    @Test
    fun `when update throws UpdateFailedException, an error notification is shown`() {
        coEvery { charactersUseCases.updateCharacters() } throws UpdateFailedException("")

        sut.updateCharacters()

        assertThat(sut.userNotification.value)
            .isEqualTo(R.string.error_updating_characters)
    }

    @Test
    fun `characters initially returns all characters`() {
        assertThat(sut.characters.value)
            .isEqualTo(allCharacters)
    }

    @Test
    fun `when search query is blank and series filter is blank then all characters returned`() {
        sut.search("")
        sut.filter("")

        assertThat(sut.characters.value)
            .isEqualTo(allCharacters)
    }

    @Test
    fun `when search query is blank and series filter is set then characters filtered by series returned`() {
        sut.search("")
        sut.filter(seriesFilter)

        assertThat(sut.characters.value)
            .isEqualTo(filteredBySeriesCharacters)
    }

    @Test
    fun `when search query is set and series filter is blank then characters by search returned`() {
        sut.search(nameSearchQuery)
        sut.filter("")

        assertThat(sut.characters.value)
            .isEqualTo(queriedByNameCharacters)
    }

    @Test
    fun `when search query is set and series filter is set then all characters by search and filter returned`() {
        sut.search(nameSearchQuery)
        sut.filter(seriesFilter)

        assertThat(sut.characters.value)
            .isEqualTo(queriedByNameAndFilteredBySeriesCharacters)
    }
}