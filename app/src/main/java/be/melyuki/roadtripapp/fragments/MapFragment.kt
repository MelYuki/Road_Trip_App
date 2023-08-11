package be.melyuki.roadtripapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.melyuki.roadtripapp.adapters.MapResearchAdapter
import be.melyuki.roadtripapp.databinding.FragmentMapBinding
import be.melyuki.roadtripapp.models.MapResearchModel
import be.melyuki.roadtripapp.services.NominatimRequest
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import kotlinx.coroutines.launch


class MapFragment private constructor(): Fragment() {

    private lateinit var binding : FragmentMapBinding
    private lateinit var adapter : BaseAdapter

    private val citiesFound : MutableList<MapResearchModel> = mutableListOf()

    companion object {
        @JvmStatic
        fun newInstance() : MapFragment {
            return MapFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

        // Chargement de la Map
        binding.mapView.getMapboxMap().apply {
            loadStyleUri(Style.MAPBOX_STREETS)
//            cameraOptions()
        }

        // Adapter
//        adapter = ArrayAdapter<MapResearchModel>(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            android.R.id.text1,
//            citiesFound // citiesFound.map { c -> c.displayName }
//        )
        adapter = MapResearchAdapter(requireContext(), citiesFound)
        binding.lvResearchCities.adapter = adapter

        // region Listeners
        binding.btnResearchCity.setOnClickListener {
            getCitiesList()
            hideKeyboard(it)
        }

        binding.mapView.getMapboxMap().addOnMapClickListener{
            clearCitiesList()
        }

        binding.lvResearchCities.setOnItemClickListener { parent, view, position, id ->

            val element = parent.getItemAtPosition(position) as MapResearchModel
//            Toast.makeText(requireContext(), "Click on '${element.displayName}' at position : $position", Toast.LENGTH_LONG).show()
            clearCitiesList()
            showCityOnMap(element)
        }
        // endregion

        return binding.root
    }

    private fun showCityOnMap(city: MapResearchModel) {

        val lat = city.lat!!.toDouble()
        val lon = city.lon!!.toDouble()

        val cameraPosition = CameraOptions.Builder()
            .zoom(9.0)
            .center(
                Point.fromLngLat(lon, lat)
            )
            .build()
        // set camera position
        binding.mapView.getMapboxMap().setCamera(cameraPosition)
    }

    private fun showCitiesList(cities: List<MapResearchModel>) {

        citiesFound.clear()
        citiesFound.addAll(cities)
        adapter.notifyDataSetChanged()
    }

    private fun clearCitiesList () : Boolean {

        citiesFound.clear()
        // Optimisation: cacher la liste et ne pas la clear
        adapter.notifyDataSetChanged()

        return true
    }

    private fun getCitiesList() {
        val city = binding.etResearchCity.text.toString()

        lifecycleScope.launch {
            try {
                val cities : List<MapResearchModel>? = NominatimRequest().searchCity(city)
                logW("Cities : ", cities.toString())
                if (cities != null) {
                    showCitiesList(cities)
                }
            }
            catch (error : Exception) {
                logW("ERROR", error.toString())
            }
        }
        binding.etResearchCity.clearFocus()
    }


}