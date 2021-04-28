package cloud.robert.mcumovies.views.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.utils.extensions.currentLocation
import cloud.robert.mcumovies.utils.extensions.locationUpdates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class LocationFragment : Fragment() {

    lateinit var locationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                getLastKnownLocation()
                locationClient.locationUpdates().observe(viewLifecycleOwner) { location ->
                    val updates = requireView().findViewById<TextView>(R.id.locationUpdates)
                    updates.text = "${location.latitude} ${location.longitude}"
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onResume() {
        super.onResume()

        // todo check if we need to display dialog to explain the permission to user
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun getLastKnownLocation() {
        lifecycleScope.launch {
            val lastKnownLocation =
                locationClient.currentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY)
            val textView = requireView().findViewById<TextView>(R.id.locationLastKnown)
            textView.text = "${lastKnownLocation?.latitude} ${lastKnownLocation?.longitude}"
        }
    }
}

