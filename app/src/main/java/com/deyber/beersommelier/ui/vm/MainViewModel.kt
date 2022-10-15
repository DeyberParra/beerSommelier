package com.deyber.beersommelier.ui.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.domain.BeersByFoodUseCase
import com.deyber.beersommelier.domain.BeersByNameUseCase
import com.deyber.beersommelier.utils.enumTypes.SearchBeersTypes
import com.deyber.beersommelier.utils.extensions.toText
import com.deyber.beersommelier.utils.resource.Resource
import com.deyber.beersommelier.utils.resource.TYPEERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val beersByName:BeersByNameUseCase,
    private val beersByFood: BeersByFoodUseCase
    ):ViewModel() {

    private val resultBeers = MutableLiveData<Resource<List<BeerModel>>>()

    fun resultBeers() = resultBeers

    fun searchData(searchType:SearchBeersTypes, query:String){
        when(searchType){
            SearchBeersTypes.NAME_BEERS->{ getBeersByName(query)}
            SearchBeersTypes.FOOD_BEERS->{ getBeersByFood(query)}
            //else->{getBeersByName(query)}
        }
    }

    fun getBeersByName(beerName:String){

        resultBeers.postValue(Resource.Loading())

        viewModelScope.launch {
            try{
              val beers = beersByName.getBeers(beerName)
                if(beers.isNullOrEmpty()){
                    resultBeers.postValue(Resource.Failure(
                        "NO TENEMOS DATOS", null, TYPEERROR.NO_DATA))
                }else{
                    resultBeers.postValue(Resource.Success(beers))
                }
            }catch(ioe:Exception){
                resultBeers.postValue(Resource.Failure(
                    "ERRO AL EJECUTAR LA SOLICITUD", null, TYPEERROR.NO_NETWORK))
            }
        }
    }

    fun getBeersByFood(foodName:String){

        resultBeers.postValue(Resource.Loading())

        viewModelScope.launch {
            try{
                val beers = beersByFood.getBeers(foodName)
                if(beers.isNullOrEmpty()){
                    resultBeers.postValue(Resource.Failure(
                        "NO TENEMOS DATOS", null, TYPEERROR.NO_DATA))
                }else{
                    resultBeers.postValue(Resource.Success(beers))
                }
            }catch(ioe:Exception){
                resultBeers.postValue(Resource.Failure(
                    "ERRO AL EJECUTAR LA SOLICITUD", null, TYPEERROR.NO_NETWORK))
            }
        }
    }

}