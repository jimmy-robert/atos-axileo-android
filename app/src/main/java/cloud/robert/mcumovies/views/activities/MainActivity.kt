package cloud.robert.mcumovies.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigation)
        val navController = findNavController(R.id.navigationFragment)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            viewModel.fetchData()
        }
    }
}