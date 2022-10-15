package com.deyber.beersommelier.ui.fragments.detailFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentAbstractBinding
import com.deyber.beersommelier.ui.vm.ShareDataViewModel
import com.deyber.beersommelier.utils.constants.RetrofitConstants.defaultImg
import com.deyber.beersommelier.utils.extensions.picaso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbstractFragment : Fragment() {

    private lateinit var binding: FragmentAbstractBinding
    private val vm: ShareDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentAbstractBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getBeerDetail().observe(viewLifecycleOwner, Observer{ beer->
            binding.beerName.text = beer.name
            binding.beerTagline.text = beer.tagline
            binding.beerFirstBrewed.text = beer.firstBrewed
            binding.beerDescription.text = beer.description
            binding.beerPhTitle.text = getString(R.string.ph_title)
            binding.beerPhData.text = beer.ph.toString()
            binding.beerAttenuationTitle.text = getString(R.string.attenuation_title)
            binding.beerAttenuationData.text= beer.attenuationLevel.toString()
            binding.beerImage.picaso(beer.imageUrl?: defaultImg)
        })
    }
}