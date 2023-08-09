package be.melyuki.roadtripapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.melyuki.roadtripapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMainResearch.setOnClickListener { goResearch() }
        binding.btnMainFav.setOnClickListener { nextActivity(FavActivity::class.java) }
        binding.btnMainRoadtrips.setOnClickListener { nextActivity(RoadTripActivity::class.java) }
    }

    private fun goResearch() {
        val county : String = binding.etMainCountry.text.toString()
        val city : String = binding.etMainCity.text.toString()

        val intent = Intent(this, ResearchActivity::class.java).apply {
            putExtra(ResearchActivity.EXTRA_COUNTRY, county)
            putExtra(ResearchActivity.EXTRA_CITY, city)
        }
        startActivity(intent)
    }

    private fun nextActivity(classe: Class<*>) {
        val intent = Intent(this, classe)
        startActivity(intent)
    }
}