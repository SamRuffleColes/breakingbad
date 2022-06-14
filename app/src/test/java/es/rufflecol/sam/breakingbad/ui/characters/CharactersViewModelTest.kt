package es.rufflecol.sam.breakingbad.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.rufflecol.sam.breakingbad.MainCoroutineRule
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val charactersRepository: CharactersRepository = mock()
    private val viewModel = CharactersViewModel(charactersRepository)

    @Before
    fun setUp() {
        whenever(charactersRepository.allCharacters).thenReturn(MutableLiveData(ALL_CHARACTERS))
        whenever(charactersRepository.searchByName(NAME_QUERY)).thenReturn(
            MutableLiveData(
                SEARCH_QUERIED_BY_NAME_CHARACTERS
            )
        )
        whenever(charactersRepository.filterBySeries(SERIES_FILTER)).thenReturn(
            MutableLiveData(
                FILTERED_BY_SERIES_CHARACTERS
            )
        )
        whenever(
            charactersRepository.searchByNameAndFilterBySeries(
                NAME_QUERY,
                SERIES_FILTER
            )
        ).thenReturn(
            MutableLiveData(
                SEARCH_QUERIED_BY_NAME_AND_FILTERED_BY_SERIES_CHARACTERS
            )
        )

        viewModel.characters.observeForever {}

    }

    @Test
    fun `when update throws exception, an error notification is shown`() {
        runBlocking {
            whenever(charactersRepository.update()).thenThrow(RuntimeException())

            viewModel.updateCharacters()

            assertThat(viewModel.userNotification.value).isEqualTo(R.string.error_updating_characters)
        }
    }

    @Test
    fun `characters initially returns all characters`() {
        assertThat(viewModel.characters.value).isEqualTo(ALL_CHARACTERS)
    }

    @Test
    fun `when search query is blank and series filter is blank then all characters returned`() {
        viewModel.search("")
        viewModel.filter("")

        assertThat(viewModel.characters.value).isEqualTo(ALL_CHARACTERS)
    }

    @Test
    fun `when search query is blank and series filter is set then characters filtered by series returned`() {
        viewModel.search("")
        viewModel.filter(SERIES_FILTER)

        assertThat(viewModel.characters.value).isEqualTo(FILTERED_BY_SERIES_CHARACTERS)
    }

    @Test
    fun `when search query is set and series filter is blank then characters by search returned`() {
        viewModel.search(NAME_QUERY)
        viewModel.filter("")

        assertThat(viewModel.characters.value).isEqualTo(SEARCH_QUERIED_BY_NAME_CHARACTERS)
    }

    @Test
    fun `when search query is set and series filter is set then all characters by search and filter returned`() {
        viewModel.search(NAME_QUERY)
        viewModel.filter(SERIES_FILTER)

        assertThat(viewModel.characters.value).isEqualTo(
            SEARCH_QUERIED_BY_NAME_AND_FILTERED_BY_SERIES_CHARACTERS
        )
    }

    companion object {
        const val NAME_QUERY = "Da"
        const val SERIES_FILTER = "S4"

        val ALL_CHARACTERS = listOf(
            CharacterEntity(0, "Tim", "", "", "", "", "S1,S2,S3,S4"),
            CharacterEntity(1, "Dan", "", "", "", "", "S1,S2,S3"),
            CharacterEntity(2, "Dave", "", "", "", "", "S1,S2,S3,S4")
        )
        val SEARCH_QUERIED_BY_NAME_CHARACTERS = listOf(
            CharacterEntity(1, "Dan", "", "", "", "", "S1,S2,S3"),
            CharacterEntity(2, "Dave", "", "", "", "", "S1,S2,S3,S4")
        )
        val FILTERED_BY_SERIES_CHARACTERS =
            listOf(
                CharacterEntity(0, "Tim", "", "", "", "", "S1,S2,S3,S4"),
                CharacterEntity(2, "Dave", "", "", "", "", "S1,S2,S3,S4")
            )

        val SEARCH_QUERIED_BY_NAME_AND_FILTERED_BY_SERIES_CHARACTERS = listOf(
            CharacterEntity(2, "Dave", "", "", "", "", "S1,S2,S3,S4")
        )

    }
}