package be.melyuki.roadtripapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.databinding.FragmentMapBinding
import com.mapbox.common.TileStoreOptions.MAPBOX_ACCESS_TOKEN
import com.mapbox.geocoder.MapboxGeocoder
import com.mapbox.maps.Style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapFragment private constructor(): Fragment() {

    private lateinit var binding : FragmentMapBinding

    companion object {
        @JvmStatic
        fun newInstance() : MapFragment {
            return MapFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

//        getGeocoderLocation()
//
//        lifecycleScope.launch(Dispatchers.Main){}

        // Chargement de la Map
        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

        return binding.root
    }

//    private fun getGeocoderLocation() {
//
//        val client = MapboxGeocoder.Builder()
//            .setAccessToken(R.string.mapbox_access_token.toString())
//            .setLocation("THE VALUE OF THE ET_MAIN_CITY")
//            .build()
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            client.execute()
//        }
//    }

}