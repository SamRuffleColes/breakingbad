package es.rufflecol.sam.breakingbad.ui.characters

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.ui.util.JobClearingViewModel
import es.rufflecol.sam.breakingbad.ui.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository,
    coroutineContext: CoroutineContext,
    job: Job
) : JobClearingViewModel(job) {

    private val coroutineScope = CoroutineScope(coroutineContext + job)

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
            seriesFilter.isBlank() && query.isBlank() -> repository.allCharacters
            query.isBlank() -> repository.filterBySeries(seriesFilter)
            seriesFilter.isBlank() -> repository.searchByName(query)
            else -> repository.searchByNameAndFilterBySeries(query, seriesFilter)
        }
    }
    val series = repository.allCharacters.map { characters ->
        characters.map { it.seriesAppearances.split(",") }
            .flatten()
            .toSortedSet()
            .filterNot { it.isBlank() }
    }
    val userNotification = SingleLiveEvent<Int>()

    fun updateCharacters() {
        coroutineScope.launch {
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