package es.rufflecol.sam.breakingbad.characters.domain.usecase

import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateCharactersUseCaseTest {

    private val repository: CharactersRepository = mockk(relaxed = true)

    private val sut = UpdateCharactersUseCase(repository)

    @Test
    fun `when invoke, then call update on repository`() = runTest {
        sut()

        coVerify { repository.update() }
    }

}