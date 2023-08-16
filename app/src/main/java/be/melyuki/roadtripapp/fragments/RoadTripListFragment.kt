package be.melyuki.roadtripapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.databinding.FragmentRoadTripListBinding

class RoadTripListFragment private constructor(): Fragment() {

    private lateinit var binding : FragmentRoadTripListBinding

    companion object {
        @JvmStatic
        fun newInstance() : RoadTripListFragment {
            return RoadTripListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoadTripListBinding.inflate(layoutInflater, container, false)


        return binding.root
    }


}