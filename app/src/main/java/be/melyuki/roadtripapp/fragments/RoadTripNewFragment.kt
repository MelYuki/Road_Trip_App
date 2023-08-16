package be.melyuki.roadtripapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.databinding.FragmentRoadTripNewBinding

class RoadTripNewFragment private constructor(): Fragment() {

    private lateinit var binding : FragmentRoadTripNewBinding

    companion object {
        @JvmStatic
        fun newInstance() : RoadTripNewFragment {
            return RoadTripNewFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoadTripNewBinding.inflate(layoutInflater, container, false)


        return binding.root
    }


}