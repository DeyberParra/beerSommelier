package com.deyber.beersommelier.ui.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.domain.BeerNetworkUseCase
import com.deyber.beersommelier.utils.resource.Resource
import com.deyber.beersommelier.utils.resource.TYPEERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val beerNetworkUseCase: BeerNetworkUseCase):ViewModel() {

    private val  responseBeers = MutableLiveData<Resource<List<BeerModel>>>()
    fun getBeers() = responseBeers

    val lastVisible = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            lastVisible.collect{ notifyLastItemVisble(it)}
        }
    }

    fun onCreate(){
        responseBeers.postValue(Resource.Loading())
        viewModelScope.launch {
            try{
                val beers = beerNetworkUseCase.getBeers()
                    if(!beers.isNullOrEmpty()){
                        responseBeers.postValue(Resource.Success(beers))
                    }else{
                        responseBeers.postValue(Resource.Failure("Error",null, TYPEERROR.NO_DATA))
                    }
            }catch(io:IOException){
                responseBeers.postValue(Resource.Failure("Error",null, TYPEERROR.NO_NETWORK))
            }

        }
    }

    private suspend fun notifyLastItemVisble(lastPosition: Int) {
        try{
            Log.i("solicitud", "estamos solicitando")
            val beers = beerNetworkUseCase.chekBeersToShow(lastPosition)
            if(!beers.isNullOrEmpty()){
                Log.i("solicitud", "tenemos los datos")
                responseBeers.postValue(Resource.Success(beers))
            }else{
                Log.i("solicitud", "no hay datos")
                responseBeers.postValue(Resource.Failure("Error",null, TYPEERROR.NO_DATA))
            }
        }catch(io:IOException){
            Log.i("solicitud", "esta muy mal la solicitud")
            responseBeers.postValue(Resource.Failure("Error",null, TYPEERROR.NO_NETWORK))
        }
    }
}