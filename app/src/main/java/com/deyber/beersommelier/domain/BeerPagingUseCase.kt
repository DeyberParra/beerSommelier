package com.deyber.beersommelier.domain

import androidx.paging.PagingData
import com.deyber.beersommelier.data.network.repository.RepositoryImplementation
import com.deyber.beersommelier.data.network.model.BeerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeerPagingUseCase @Inject constructor(private val repository: RepositoryImplementation) {

    fun getBeers(): Flow<PagingData<BeerModel>> {
        return repository
            .getBeersforPage()
            .getBeers()
    }

}