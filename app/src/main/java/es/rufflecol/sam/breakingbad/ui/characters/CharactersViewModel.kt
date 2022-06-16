package es.rufflecol.sam.breakingbad.ui.characters

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.common.coroutine.CoroutineDispatchProvider
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.ui.util.SingleLiveEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository,
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
        when {
            seriesFilter.isBlank() && query.isBlank() -> repository.allCharacters.asLiveData()
            query.isBlank() -> repository.filterBySeries(seriesFilter).asLiveData()
            seriesFilter.isBlank() -> repository.searchByName(query).asLiveData()
            else -> repository.searchByNameAndFilterBySeries(query, seriesFilter).asLiveData()
        }
    }
    val series = repository.allCharacters.map { characters ->
        characters.map { it.seriesAppearances.split(",") }
            .flatten()
            .toSortedSet()
            .filterNot { it.isBlank() }
    }.asLiveData()
    val userNotification = SingleLiveEvent<Int>()

    fun updateCharacters() {
        viewModelScope.launch(coroutineDispatchProvider.io) {
            try {
                repository.update()
            } catch (e: Exception) {
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