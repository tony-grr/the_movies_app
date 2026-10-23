package com.example.themoviesapp.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.themoviesapp.domain.repository.genre.GenreRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first

@HiltWorker
class RefreshGenresWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val genreRepository: GenreRepository,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val lang = settingsRepository.getLanguage().first()
            genreRepository.refreshGenres(lang)
            Result.success()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "RefreshGenresWorker"
    }
}
