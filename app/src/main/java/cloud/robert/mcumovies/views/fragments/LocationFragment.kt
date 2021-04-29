package cloud.robert.mcumovies.views.fragments

import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationFragment : Fragment() {

    @Inject
    lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var sensorManager: SensorManager

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

        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach {
            Log.i("MCU Movies", "${it.name} - ${it.stringType}")
        }
    }

    override fun onResume() {
        super.onResume()

        // todo check if we need to display dialog to explain the permission to user
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listener)
    }

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            Log.i("MCU Movies", event?.values?.joinToString(",") ?: "Nothing to show")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
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

