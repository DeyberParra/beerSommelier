package com.deyber.beersommelier.domain

import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.data.network.repository.RepositoryImplementation
import javax.inject.Inject

class BeersByFoodUseCase @Inject constructor(private val repotory:RepositoryImplementation) {

    suspend fun getBeers(foodBeer:String):List<BeerModel>?{
        return repotory.getBeersByFood(foodBeer)
    }

}