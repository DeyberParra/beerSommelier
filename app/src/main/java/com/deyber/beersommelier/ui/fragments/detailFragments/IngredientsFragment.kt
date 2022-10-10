package com.deyber.beersommelier.ui.fragments.detailFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentIngredientsBinding
import com.deyber.beersommelier.ui.vm.DetailViewModel
import com.deyber.beersommelier.utils.constants.RetrofitConstants.defaultImg
import com.deyber.beersommelier.utils.extensions.*

class IngredientsFragment : Fragment() {

    private lateinit var binding:FragmentIngredientsBinding
    private val vm: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentIngredientsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.beerData.observe(viewLifecycleOwner, Observer{ beer->

            binding.beerIngredients.text = getString(R.string.ingredients)
            binding.beerHops.text = getString(R.string.hops)
            binding.beerFermentation.text = getString(R.string.fermentation)

            beer.method?.mashTemp?.let { binding.beerDescriptionMethod.mashTemp(it) }
            beer.ingredients?.hops?.let{binding.beerDescriptionHops.hops(it)}
            beer.ingredients?.malt?.let{binding.beerDescriptionIngredients.malt(it)}
            binding.beerName.text = beer.name
            binding.beerImage.picaso(beer.imageUrl?:defaultImg)
            binding.beerDescriptionFermentation.text = "${beer.method?.fermentation?.temp?.value} ${beer.method?.fermentation?.temp?.unit}"
            binding.beerMethod.text = getString(R.string.method)

        })
    }
}