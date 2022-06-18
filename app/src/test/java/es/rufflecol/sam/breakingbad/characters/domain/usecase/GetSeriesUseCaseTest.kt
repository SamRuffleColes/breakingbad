package es.rufflecol.sam.breakingbad.characters.domain.usecase

import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.testCharacter
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetSeriesUseCaseTest {

    companion object {
        private const val series1 = "series_1_name"
        private const val series2 = "series_2_name"
        private const val series3 = "series_3_name"
        private const val series4 = "series_4_name"
    }

    private val repository: CharactersRepository = mockk(relaxed = true)

    private val sut = GetSeriesUseCase(repository)

    @Test
    fun `given characters is empty, when invoke, then return empty list`() = runTest {
        every { repository.allCharacters } returns flowOf(emptyList())

        val result = sut()

        assertThat(result.first()).isEmpty()
    }

    @Test
    fun `given no series appearances, when invoke, then return empty list`() = runTest {
        val character = testCharacter(seriesAppearances = "")
        every { repository.allCharacters } returns flowOf(listOf(character))

        val result = sut()

        assertThat(result.first()).isEmpty()
    }

    @Test
    fun `when invoke, then return series with duplicates removed`() = runTest {
        val firstCharacter = testCharacter(id = 0, seriesAppearances = "$series1,$series2")
        val secondCharacter =
            testCharacter(id = 1, seriesAppearances = "$series1,$series2,$series3,$series4")
        every { repository.allCharacters } returns flowOf(listOf(firstCharacter, secondCharacter))

        val result = sut()

        assertThat(result.first())
            .isEqualTo(listOf(series1, series2, series3, series4))
    }

    @Test
    fun `when invoke, then return series sorted`() = runTest {
        val character = testCharacter(seriesAppearances = "$series4,$series2,$series1,$series3")
        every { repository.allCharacters } returns flowOf(listOf(character))

        val result = sut()

        assertThat(result.first())
            .isEqualTo(listOf(series1, series2, series3, series4))
    }

    @Test
    fun `given series contains blanks, when invoke, then remove blank series`() = runTest {
        val character = testCharacter(seriesAppearances = "$series1,,$series4,,,")
        every { repository.allCharacters } returns flowOf(listOf(character))

        val result = sut()

        assertThat(result.first())
            .isEqualTo(listOf(series1, series4))
    }
}