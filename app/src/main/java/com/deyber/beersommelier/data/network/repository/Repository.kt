package com.deyber.beersommelier.data.network.repository

import com.deyber.beersommelier.data.network.repository.dataSource.BeerDataSourceImplementation
import com.deyber.beersommelier.data.network.model.BeerModel

interface Repository {

    suspend fun getBeersByName(name:String):List<BeerModel>?
    suspend fun getBeersByFood(food:String):List<BeerModel>?
    fun getBeersforPage(): BeerDataSourceImplementation

}