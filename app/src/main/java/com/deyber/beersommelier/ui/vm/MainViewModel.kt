package com.deyber.beersommelier.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.domain.BeerNetworkUseCase
import com.deyber.beersommelier.utils.resource.Resource
import com.deyber.beersommelier.utils.resource.TYPEERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val beerNetworkUseCase: BeerNetworkUseCase):ViewModel() {
    private val  responseBeers = MutableLiveData<Resource<List<BeerModel>>>()

    fun getBeers() = responseBeers

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
}