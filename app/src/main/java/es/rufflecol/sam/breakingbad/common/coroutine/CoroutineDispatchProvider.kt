package es.rufflecol.sam.breakingbad.common.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}