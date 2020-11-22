package es.rufflecol.sam.breakingbad.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import es.rufflecol.sam.breakingbad.ui.util.JobClearingViewModel
import es.rufflecol.sam.breakingbad.ui.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CharactersViewModel @ViewModelInject constructor(
    private val repository: CharactersRepository,
    coroutineContext: CoroutineContext,
    job: Job
) : JobClearingViewModel(job) {

    private val coroutineScope = CoroutineScope(coroutineContext + job)

    private val query = MutableLiveData("")
    val characters: LiveData<List<CharacterEntity>> = query.switchMap { query ->
        if (query.isNullOrBlank()) {
            repository.allCharacters
        } else {
            repository.searchByName(query)
        }
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

}