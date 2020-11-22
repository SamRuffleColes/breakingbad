package es.rufflecol.sam.breakingbad.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val charactersRepository: CharactersRepository = mock()
    private val viewModel =
        CharactersViewModel(charactersRepository, Dispatchers.Unconfined, SupervisorJob())

    @Before
    fun setUp() {
        whenever(charactersRepository.allCharacters).thenReturn(MutableLiveData(ALL_CHARACTERS))
        whenever(charactersRepository.searchByName(QUERY)).thenReturn(
            MutableLiveData(
                QUERIED_CHARACTERS
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
    fun `when search query is empty characters returns all characters`() {
        viewModel.search("")

        assertThat(viewModel.characters.value).isEqualTo(ALL_CHARACTERS)
    }

    @Test
    fun `when search query is set characters returns by search`() {
        viewModel.search(QUERY)

        assertThat(viewModel.characters.value).isEqualTo(QUERIED_CHARACTERS)
    }

    companion object {
        val ALL_CHARACTERS = listOf(
            CharacterEntity(0, "Tim", "", "", "", "", ""),
            CharacterEntity(1, "Dan", "", "", "", "", ""),
            CharacterEntity(2, "Dave", "", "", "", "", "")
        )
        const val QUERY = "Da"
        val QUERIED_CHARACTERS = listOf(
            CharacterEntity(1, "Dan", "", "", "", "", ""),
            CharacterEntity(2, "Dave", "", "", "", "", "")
        )
    }
}