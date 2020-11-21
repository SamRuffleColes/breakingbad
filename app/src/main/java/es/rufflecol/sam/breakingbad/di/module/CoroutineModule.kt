package es.rufflecol.sam.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ActivityComponent::class)
object CoroutineModule {

    @Provides
    fun provideJob(): Job = SupervisorJob()

    @Provides
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

}