package be.melyuki.roadtripapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.database.dao.FavDao
import be.melyuki.roadtripapp.databinding.ActivityResearchBinding
import be.melyuki.roadtripapp.fragments.MapFragment
import be.melyuki.roadtripapp.models.CityModel
import com.rw.keyboardlistener.KeyboardUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ResearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResearchBinding

    private var fabsVisible : Boolean = false

    private var citySelected : CityModel? = null
    private val favDao = FavDao(this)
    private val TAG : String = "TEST"

    private var isLiked : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMapFrag()
        hideFabs()
        binding.fabLikeBtn.isEnabled = false

        binding.fabOptionBtn.setOnClickListener { getOptionMenu() }

        // Reaction à l'élévation du clavier
        // Solution
        // https://github.com/ravindu1024/android-keyboardlistener
        KeyboardUtils.addKeyboardToggleListener(this) { isVisible ->
            if (isVisible) hideFabs()
        }

        favDao.openWritable()
        binding.fabLikeBtn.setOnClickListener {
            if(citySelected == null) return@setOnClickListener

//            Toast.makeText(this, "fabLike ACTIVATED", Toast.LENGTH_LONG).show() // -> OK
//            Log.w(TAG, "Before Create: $citySelected") // -> OK
//            favDao.create(citySelected!!)
            updateFavList(citySelected!!)
//            val cities : List<CityModel> = favDao.getAll()
//            for (city in cities) {
//                Log.w(TAG, "Dao Create/GetAll: $city") // -> OK
//            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        favDao.close()
    }

    private fun updateFavList(city: CityModel) {

        if (!switchFabLike(city)) {
            lifecycleScope.launch(Dispatchers.IO) {
                favDao.create(city)
            }
            Toast.makeText(this, R.string.toast_add_to_favlist, Toast.LENGTH_LONG).show()
        }
        else {
            lifecycleScope.launch(Dispatchers.IO) {
                favDao.delete(city.placeId!!.toLong())
            }
            Toast.makeText(this, R.string.toast_del_from_favlist, Toast.LENGTH_LONG).show()
        }
        switchFabLike(city)
    }
    private fun switchFabLike(city: CityModel) : Boolean {
        val result = favDao.getById(city.placeId!!.toLong())
//        Log.w(TAG, "checkCityInDb: $result") // -> OK
        isLiked = result != null
//        Log.w(TAG, "isLiked: $isLiked", ) // -> OK

        return if (isLiked) {
            binding.tvFabLike.text = getString(R.string.tv_text_fab_research_unlike)
            binding.fabLikeBtn.setImageDrawable(this.getDrawable(R.drawable.fab_icon_star_liked))
            true
        } else {
            binding.tvFabLike.text = getString(R.string.tv_text_fab_research_like)
            binding.fabLikeBtn.setImageDrawable(this.getDrawable(R.drawable.fab_icon_star))
            false
        }
    }

    private fun getOptionMenu() {
        if(!fabsVisible) {
            showFabs()
        }
        else {
            hideFabs()
        }
    }
    private fun showFabs() {

        binding.fabLikeBtn.show()
        binding.fabDistanceBtn.show()
        binding.fabPoiBtn.show()
        binding.fabWeatherBtn.show()
        binding.fabRoadtripBtn.show()

        binding.tvFabLike.visibility = View.VISIBLE
        binding.tvFabDistance.visibility = View.VISIBLE
        binding.tvFabWeather.visibility = View.VISIBLE
        binding.tvFabPoi.visibility = View.VISIBLE
        binding.tvFabRoadtrip.visibility = View.VISIBLE

        binding.fabOptionBtn.setImageDrawable(this.getDrawable(R.drawable.fab_icon_arrow_up))

        fabsVisible = true
    }
    private fun hideFabs() {

        binding.fabLikeBtn.hide()
        binding.fabDistanceBtn.hide()
        binding.fabPoiBtn.hide()
        binding.fabWeatherBtn.hide()
        binding.fabRoadtripBtn.hide()

        binding.tvFabLike.visibility = View.GONE
        binding.tvFabDistance.visibility = View.GONE
        binding.tvFabWeather.visibility = View.GONE
        binding.tvFabPoi.visibility = View.GONE
        binding.tvFabRoadtrip.visibility = View.GONE

        binding.fabOptionBtn.setImageDrawable(this.getDrawable(R.drawable.fab_icon_arrow))

        fabsVisible = false
    }

    private fun loadMapFrag() {

        val fragMap : MapFragment = MapFragment.newInstance()

        // Ecriture Interface Fonctionnelle (version Lambda)
//        fragMap.setOnCitySelectedListener { selected ->
//            binding.fabLikeBtn.isClickable = selected
//        }

        // Ecriture Interface Fonctionnelle et NON-Fonctionnelle
        fragMap.setOnCitySelectedListener(object : MapFragment.OnActionListener {
            override fun onCitySelected(city : CityModel?) {
                binding.fabLikeBtn.isEnabled = city != null
                citySelected = city
//                Log.w(TAG, "Content citySelected: $citySelected") // -> OK

                // Ici qu'on va gérer si la ville est déjà liked -> check DB
                switchFabLike(citySelected!!)
            }
        })

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv_research_content, fragMap)
        }
    }

}