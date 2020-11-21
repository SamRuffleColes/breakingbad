package es.rufflecol.sam.breakingbad.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
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

    @Test
    fun `when update throws exception, an error notification is shown`() {
        runBlocking {
            whenever(charactersRepository.updateCharacters()).thenThrow(RuntimeException())

            viewModel.updateCharacters()

            assertThat(viewModel.userNotification.value).isEqualTo(R.string.error_updating_characters)
        }
    }


}