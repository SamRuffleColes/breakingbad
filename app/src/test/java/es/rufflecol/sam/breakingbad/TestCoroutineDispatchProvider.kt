package es.rufflecol.sam.breakingbad

import es.rufflecol.sam.breakingbad.common.coroutine.CoroutineDispatchProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestCoroutineDispatchProvider : CoroutineDispatchProvider {
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
    override val io: CoroutineDispatcher = Dispatchers.Unconfined
}