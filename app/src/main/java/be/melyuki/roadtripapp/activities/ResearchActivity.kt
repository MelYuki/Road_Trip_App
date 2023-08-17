package be.melyuki.roadtripapp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.databinding.ActivityResearchBinding
import be.melyuki.roadtripapp.fragments.MapFragment
import com.rw.keyboardlistener.KeyboardUtils


class ResearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResearchBinding

    private var fabsVisible : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMapFrag()

        hideFabs()

        binding.fabOptionBtn.setOnClickListener { getOptionMenu() }

        // Reaction à l'élévation du clavier
        // Solution
        // https://github.com/ravindu1024/android-keyboardlistener
        KeyboardUtils.addKeyboardToggleListener(this) { isVisible ->
            if (isVisible) hideFabs()
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

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv_research_content, fragMap)
        }
    }

}