package be.melyuki.roadtripapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import be.melyuki.roadtripapp.adapters.ListFavAdapter
import be.melyuki.roadtripapp.database.dao.FavDao
import be.melyuki.roadtripapp.databinding.ActivityFavBinding
import be.melyuki.roadtripapp.models.CityModel

class FavActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavBinding

    private lateinit var favCities : MutableList<CityModel>

    private val favDao = FavDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favDao.openReadable()

        // region RecyclerView
        favCities = mutableListOf()

        val adapter : ListFavAdapter = ListFavAdapter(this, favCities)
        binding.rvFavCities.adapter = adapter
        binding.rvFavCities.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        // endregion
    }
    override fun onStart() {
        super.onStart()

        // Nettoyage de la liste (après modification*s de celle-ci)
        favCities.clear()
        // Chargement des villes dans la liste
        favCities.addAll(favDao.getAll())
        // Notifier la RecyclerView des potentiels changements
        binding.rvFavCities.adapter?.notifyDataSetChanged()
    }
    override fun onDestroy() {
        super.onDestroy()

        favDao.close()
    }
}