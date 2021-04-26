package cloud.robert.mcumovies.viewmodels

import androidx.lifecycle.ViewModel
import cloud.robert.mcumovies.business.repositories.Repository

class MovieViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    fun getMovie(id: Int) = repository.getMovie(id)
}