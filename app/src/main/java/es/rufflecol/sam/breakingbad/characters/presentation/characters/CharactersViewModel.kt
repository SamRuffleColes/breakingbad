package es.rufflecol.sam.breakingbad.characters.presentation.characters

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.common.coroutine.CoroutineDispatchProvider
import es.rufflecol.sam.breakingbad.characters.domain.model.exception.UpdateFailedException
import es.rufflecol.sam.breakingbad.characters.domain.usecase.CharactersUseCases
import es.rufflecol.sam.breakingbad.common.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersUseCases: CharactersUseCases,
    private val coroutineDispatchProvider: CoroutineDispatchProvider
) : ViewModel() {

    private val query = MutableLiveData("")
    private val seriesFilter = MutableLiveData<String>()
    private val queryAndSeriesFilters = MediatorLiveData<Pair<String, String>>().apply {
        addSource(query) {
            value = Pair(it, seriesFilter.value.orEmpty())
        }
        addSource(seriesFilter) {
            value = Pair(query.value.orEmpty(), it)
        }
    }
    val characters = queryAndSeriesFilters.switchMap { pair ->
        val query = pair.first
        val seriesFilter = pair.second
        charactersUseCases.getCharacters(query, seriesFilter).asLiveData()
    }
    val series = charactersUseCases.getSeries().asLiveData()
    val userNotification = SingleLiveEvent<Int>()

    fun updateCharacters() {
        viewModelScope.launch(coroutineDispatchProvider.io) {
            try {
                charactersUseCases.updateCharacters()
            } catch (e: UpdateFailedException) {
                userNotification.postValue(R.string.error_updating_characters)
            }
        }
    }

    fun search(query: String) {
        this.query.value = query
    }

    fun filter(series: String) {
        this.seriesFilter.value = series
    }

}