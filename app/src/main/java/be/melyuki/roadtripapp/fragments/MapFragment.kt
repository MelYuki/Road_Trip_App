package be.melyuki.roadtripapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.melyuki.roadtripapp.databinding.FragmentMapBinding
import be.melyuki.roadtripapp.models.MapResearchModel
import be.melyuki.roadtripapp.services.NominatimRequest
import com.mapbox.maps.Style
import com.mapbox.maps.logW
import kotlinx.coroutines.launch


class MapFragment private constructor(): Fragment() {

    private lateinit var binding : FragmentMapBinding
    private lateinit var adapter : ArrayAdapter<MapResearchModel>

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

        // Chargement de la Map
        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

        // Listener
        binding.btnResearchCity.setOnClickListener { getCitiesList() }

        // Adapter
        adapter = ArrayAdapter<MapResearchModel>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            citiesFound // citiesFound.map { c -> c.displayName }
        )
        binding.lvResearchCities.adapter = adapter

        return binding.root
    }

    private fun showCitiesList(cities: List<MapResearchModel>) {

//        for (element in cities) {
//            val city = MapResearchModel(element.displayName)
//            logW("city", city.toString())
//
//        }

        citiesFound.clear()
        citiesFound.addAll(cities)
        adapter.notifyDataSetChanged()

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