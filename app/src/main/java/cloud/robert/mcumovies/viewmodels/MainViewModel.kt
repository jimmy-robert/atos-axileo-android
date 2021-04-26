package cloud.robert.mcumovies.viewmodels

import androidx.lifecycle.ViewModel
import cloud.robert.mcumovies.business.repositories.Repository

class MainViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    suspend fun fetchData() = repository.fetchData()

    val movies = repository.getMovies()

    fun getMovie(id: Int) = repository.getMovie(id)
}