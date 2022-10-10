package com.deyber.beersommelier.ui.fragments.detailFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.deyber.beersommelier.databinding.FragmentFoodParingBinding
import com.deyber.beersommelier.ui.vm.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodParingFragment : Fragment() {

    private lateinit var binding:FragmentFoodParingBinding
    private val vm: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentFoodParingBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.beerData.observe(viewLifecycleOwner, Observer{

        })
    }
}