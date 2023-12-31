package be.melyuki.roadtripapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.melyuki.roadtripapp.adapters.MapResearchAdapter
import be.melyuki.roadtripapp.databinding.FragmentMapBinding
import be.melyuki.roadtripapp.models.CityModel
import be.melyuki.roadtripapp.services.NominatimRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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

    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private val citiesFound : MutableList<CityModel> = mutableListOf()

    companion object {
        @JvmStatic
        fun newInstance() : MapFragment {
            return MapFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation du Provider du LocationService d'android
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

        // Chargement de la Map
//        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
//        logW("APPEL DE LA FONCTION", "GetUserLocation()") // -> OK
        getUserLocation()

        // Adapter
//        adapter = ArrayAdapter<CityModel>(
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
            binding.lvResearchCities.visibility = View.VISIBLE
        }
        binding.etResearchCity.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) binding.lvResearchCities.visibility = View.VISIBLE
        }

        binding.mapView.getMapboxMap().addOnMapClickListener{
            hideCitiesList()
            releaseFocus()
        }

        binding.lvResearchCities.setOnItemClickListener { parent, view, position, id ->

            val element = parent.getItemAtPosition(position) as CityModel
//            Toast.makeText(requireContext(), "Click on '${element.displayName}' at position : $position", Toast.LENGTH_LONG).show()
            hideCitiesList()
            showCityOnMap(element)

            // Dire au parent de rendre le fab_like "Enable"
            onCitySelectedListener?.onCitySelected(element)

            // Dans le parent, Au click du fab_like:
            // - Ajouter l'élément à la DB
            // - Dire la FavActivity de charger la liste de la DB
            // - Changer l'icone et le tv du fab_like
        }
        // endregion

        return binding.root
    }

    // region EventListener to Activity
    fun interface OnActionListener {
        fun onCitySelected(city : CityModel?)
    }

    // ↓ Cette variable va contenir un objet de type "OnActionListener" et donc, on peut réaliser les méthodes depuis celui-ci
    private var onCitySelectedListener : OnActionListener? = null

    fun setOnCitySelectedListener(listener: OnActionListener) {
        // Cette fonction ne doit être appeler que par le parent pour lui communiqué le listener à exploiter
        onCitySelectedListener = listener
    }
    // endregion

    @SuppressLint("RestrictedApi")
    private fun releaseFocus() : Boolean {
        binding.etResearchCity.apply {
            clearFocus()
            view?.let { hideKeyboard(it) }
        }
        return true
    }

    // @SuppressLint("MissingPermission")
    private fun getUserLocation() {

//        logW("APPEL DE LA FONCTION", "CheckLocationPermission()") // -> OK
        checkLocationPermission()

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location : Location ->
//                logW("APPEL DE LA FONCTION", "showUserLocationOnMap()")
                showUserLocationOnMap(location)
            }
    }

    private fun showUserLocationOnMap(location: Location) {

        val userLon = location.longitude
        val userLat = location.latitude

//        logW("LOCATION", "$userLon / $userLat") // -> ne passe pas par là !!
        // Sans le @SuppressLint("MissingPermission") de la fonction getUserLocation() ...
        // -> C'EST OKKKKKKKKKK !!!!!!

        val cameraPosition = CameraOptions.Builder()
            .zoom(15.0)
            .center(
                Point.fromLngLat(userLon, userLat)
            )
            .build()
        // set camera position
        binding.mapView.getMapboxMap().apply {
            loadStyleUri(Style.MAPBOX_STREETS)
            setCamera(cameraPosition)
        }
    }

    private fun checkLocationPermission() {

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(requireContext(), "TOO BAD!\nIt will miss some features!", Toast.LENGTH_LONG).show()
                // Si REFUS du user,
                // Empêcher l'accès à certaines fonctionnalités. -> Boolean
            }
        }

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showCityOnMap(city: CityModel) {

        val lon = city.lon!!.toDouble()
        val lat = city.lat!!.toDouble()

        val cameraPosition = CameraOptions.Builder()
            .zoom(12.0)
            .center(
                Point.fromLngLat(lon, lat)
            )
            .build()
        // set camera position
        binding.mapView.getMapboxMap().setCamera(cameraPosition)
    }

    private fun showCitiesList(cities: List<CityModel>) {

        citiesFound.clear()
        citiesFound.addAll(cities)
        adapter.notifyDataSetChanged()
    }

    private fun hideCitiesList () : Boolean {

//        citiesFound.clear()
        // Optimisation: cacher la liste et ne pas la clear
        binding.lvResearchCities.visibility = View.GONE
//        adapter.notifyDataSetChanged()

        return true
    }

    private fun getCitiesList() {
        val city = binding.etResearchCity.text.toString()

        lifecycleScope.launch {
            try {
                val cities : List<CityModel>? = NominatimRequest().searchCity(city)
//                logW("Cities : ", cities.toString())
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