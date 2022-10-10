package com.deyber.beersommelier.data.network.Repository

import com.deyber.beersommelier.data.network.BeerClient
import com.deyber.beersommelier.data.network.model.BeerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val beerClient: BeerClient):Repository {
    override suspend fun getBeers(): List<BeerModel>?{
        return withContext(Dispatchers.IO){
            val response = beerClient.getBeers()
            response.body()
        }
    }
}