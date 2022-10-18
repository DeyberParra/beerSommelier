package com.deyber.beersommelier.data.network

import com.deyber.beersommelier.data.network.model.BeerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerClient {

     @GET("beers")
     suspend fun getBeersforPage(@Query("page") page: Int):Response<List<BeerModel>>

     @GET("beers")
     suspend fun getBeersByName(@Query("beer_name") name:String):Response<List<BeerModel>>

     @GET("beers")
     suspend fun getBeersByFood(@Query("food")food:String):Response<List<BeerModel>>

}
