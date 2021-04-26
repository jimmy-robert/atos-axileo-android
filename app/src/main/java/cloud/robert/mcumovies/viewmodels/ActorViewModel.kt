package cloud.robert.mcumovies.viewmodels

import androidx.lifecycle.ViewModel
import cloud.robert.mcumovies.business.repositories.Repository

class ActorViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    fun getActor(id: Int) = repository.getActor(id)
}