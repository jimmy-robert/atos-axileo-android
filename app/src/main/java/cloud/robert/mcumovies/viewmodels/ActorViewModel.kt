package cloud.robert.mcumovies.viewmodels

import androidx.lifecycle.ViewModel
import cloud.robert.mcumovies.business.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getActor(id: Int) = repository.getActor(id)
}