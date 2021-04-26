package cloud.robert.mcumovies.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cloud.robert.mcumovies.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.movies.observe(this) {
            Log.i("MCU Movies", "Found ${it.size} movies")
        }

        viewModel.getMovie(1726).observe(this) {
            Log.i("MCU Movies", "Found ${it.movie.title} with ${it.actors.size} actors")
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            viewModel.fetchData()
        }
    }
}