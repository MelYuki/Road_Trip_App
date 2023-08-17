package be.melyuki.roadtripapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.adapters.ListFavAdapter
import be.melyuki.roadtripapp.databinding.ActivityFavBinding
import be.melyuki.roadtripapp.models.MapResearchModel

class FavActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavBinding

    private lateinit var favCities : MutableList<MapResearchModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}