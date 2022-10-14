package com.deyber.beersommelier.data.network.repository

import com.deyber.beersommelier.data.network.repository.dataSource.BeerDataSourceImplementation
import com.deyber.beersommelier.data.network.model.BeerModel

interface Repository {

    suspend fun getBeers():List<BeerModel>?
    fun getBeersforPage(): BeerDataSourceImplementation
}