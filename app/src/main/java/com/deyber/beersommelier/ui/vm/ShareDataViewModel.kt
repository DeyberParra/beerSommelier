package com.deyber.beersommelier.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.utils.enumTypes.SearchBeersTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareDataViewModel @Inject constructor():ViewModel() {
    private val beerDetail = MutableLiveData<BeerModel>()
    private val searchBeersTypes = MutableLiveData<SearchBeersTypes>().apply {
        value =  SearchBeersTypes.NAME_BEERS
    }

    fun selectBeerDetail(beer:BeerModel){
        beerDetail.value = beer
    }

    fun selectSearchBeerType(type:SearchBeersTypes){
        searchBeersTypes.value = type
    }

    fun getSearchBeerType(): MutableLiveData<SearchBeersTypes> {
        return searchBeersTypes
    }
    fun getBeerDetail() = beerDetail


}