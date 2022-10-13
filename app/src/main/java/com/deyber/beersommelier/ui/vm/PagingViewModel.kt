package com.deyber.beersommelier.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.deyber.beersommelier.data.network.repository.RepositoryImplementation
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.data.network.repository.dataSource.BeersPagingSource
import com.deyber.beersommelier.domain.BeerPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val beerPagingUseCase: BeerPagingUseCase):ViewModel(){

    fun getBeers():Flow<PagingData<BeerModel>>{
        return beerPagingUseCase
            .getBeers()
            .cachedIn(viewModelScope)
    }

}