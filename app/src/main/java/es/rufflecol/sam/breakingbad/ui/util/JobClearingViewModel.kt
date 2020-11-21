package es.rufflecol.sam.breakingbad.ui.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class JobClearingViewModel(private val job: Job) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}