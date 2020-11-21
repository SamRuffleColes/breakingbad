package es.rufflecol.sam.breakingbad.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
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

    val characters = repository.characters
    val userNotification = SingleLiveEvent<Int>()

    fun updateCharacters() {
        coroutineScope.launch {
            try {
                repository.updateCharacters()
            } catch (e: Exception) {
                userNotification.postValue(R.string.error_updating_characters)
            }
        }
    }

}