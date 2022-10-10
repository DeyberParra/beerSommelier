package com.deyber.beersommelier.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deyber.beersommelier.data.network.model.BeerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor():ViewModel() {
     val beerData =  MutableLiveData<BeerModel>()

    fun select(beer:BeerModel){
        beerData.value = beer
    }
}