package be.melyuki.roadtripapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.databinding.ActivityRoadTripBinding
import be.melyuki.roadtripapp.fragments.RoadTripListFragment
import be.melyuki.roadtripapp.fragments.RoadTripNewFragment

class RoadTripActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRoadTripBinding

    private val fragNew : RoadTripNewFragment = RoadTripNewFragment.newInstance()
    private val fragList : RoadTripListFragment = RoadTripListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoadTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadInitialFrag()

        binding.btnRtList.setOnClickListener { loadListRtFrag() }
        binding.btnRtNew.setOnClickListener { loadNewRtFrag() }
    }

    private fun loadListRtFrag() {
        switchFragAnimation(fragList)
        binding.btnRtNew.setTextColor(this.getColor(R.color.purple))
        binding.btnRtList.setTextColor(this.getColor(R.color.yellow))
    }
    private fun loadNewRtFrag() {
        switchFragAnimation(fragNew)
        binding.btnRtNew.setTextColor(this.getColor(R.color.yellow))
        binding.btnRtList.setTextColor(this.getColor(R.color.purple))
    }
    private fun switchFragAnimation(frag : Fragment) {

        supportFragmentManager.commit {
            setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            setReorderingAllowed(true)
            replace(R.id.fcv_rt_content, frag)
        }
    }

    private fun loadInitialFrag() {

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv_rt_content, fragNew)
        }
    }
}