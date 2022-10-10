package com.deyber.beersommelier.data.network

import com.deyber.beersommelier.data.network.model.BeerModel
import retrofit2.Response
import retrofit2.http.GET

interface BeerClient {

     @GET("beers")
     suspend fun getBeers():Response<List<BeerModel>>
}