package be.melyuki.roadtripapp

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

        binding.btnMainResearch.setOnClickListener { nextActivity(ResearchActivity::class.java) }
        binding.btnMainFav.setOnClickListener { nextActivity(FavActivity::class.java) }
        binding.btnMainRoadtrips.setOnClickListener { nextActivity(RoadTripActivity::class.java) }
    }

    private fun nextActivity(classe: Class<*>) {
        val intent = Intent(this, classe)
        startActivity(intent)
    }
}