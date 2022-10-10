package com.deyber.beersommelier.data.network.Repository

import com.deyber.beersommelier.data.network.model.BeerModel

interface Repository {

    suspend fun getBeers():List<BeerModel>?
}