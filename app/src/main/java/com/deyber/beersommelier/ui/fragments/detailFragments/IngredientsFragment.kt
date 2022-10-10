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
import com.deyber.beersommelier.utils.extensions.picaso
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
            var mashTemp =""
            var malt = ""
            var hops = ""


            beer.method?.mashTemp?.map {
                mashTemp+="${it.temp?.value} ${it.temp?.unit} \r\n"
            }
            beer.ingredients?.malt?.map {
                malt += "${it.name}  ${it.amount?.value} ${it.amount?.unit}\r\n "
            }
            beer.ingredients?.hops?.map {
                hops+="${it.name} ${it.attribute} ${it.amount?.value} ${it.amount?.unit}  ${it.add}\r\n"
            }

            binding.beerDescriptionMethod.text = mashTemp
            binding.beerIngredients.text = getString(R.string.ingredients)
            binding.beerDescriptionIngredients.text= malt
            binding.beerHops.text = getString(R.string.hops)
            binding.beerDescriptionHops.text = hops
            binding.beerImage.picaso(beer.imageUrl?:defaultImg)
            binding.beerName.text = beer.name
            binding.beerFermentation.text = getString(R.string.fermentation)
            binding.beerDescriptionFermentation.text = "${beer.method?.fermentation?.temp?.value} ${beer.method?.fermentation?.temp?.unit}"
            binding.beerMethod.text = getString(R.string.method)

        })
    }
}