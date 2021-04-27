package cloud.robert.mcumovies.viewmodels

import androidx.lifecycle.ViewModel
import cloud.robert.mcumovies.business.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun fetchData() = repository.fetchData()

    val movies = repository.getMovies()

    val actors = repository.getActors()
}