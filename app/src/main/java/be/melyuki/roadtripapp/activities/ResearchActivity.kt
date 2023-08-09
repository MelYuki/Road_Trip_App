package be.melyuki.roadtripapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.databinding.ActivityResearchBinding
import be.melyuki.roadtripapp.fragments.MapFragment

class ResearchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COUNTRY = "extra_country"
        const val EXTRA_CITY = "extra_city"
    }

    private lateinit var binding : ActivityResearchBinding

    private lateinit var country : String
    private lateinit var city : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        country = intent.getStringExtra(EXTRA_COUNTRY).toString()
        city = intent.getStringExtra(EXTRA_CITY).toString()

        loadMapFrag()
    }

    private fun loadMapFrag() {

        val fragMap : MapFragment = MapFragment.newInstance()

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv_research_content, fragMap)
        }
    }
}