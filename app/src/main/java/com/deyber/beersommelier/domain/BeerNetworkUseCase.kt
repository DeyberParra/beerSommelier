package com.deyber.beersommelier.domain

import android.util.Log
import com.deyber.beersommelier.data.network.Repository.Repository
import com.deyber.beersommelier.data.network.Repository.RepositoryImplementation
import com.deyber.beersommelier.data.network.model.BeerModel
import javax.inject.Inject

class BeerNetworkUseCase @Inject constructor(private val repo: RepositoryImplementation):Repository {

    companion object{
        const val TOTAL_BERRS:Int = 160
        const val BEER_FOR_PAGE:Int =25
        const val MIN_BEER_FOR_LOAD:Int=15
    }

    override suspend fun getBeers(): List<BeerModel>? {
        return repo.getBeers()
    }

    suspend fun chekBeersToShow(lastVisible:Int):List<BeerModel>? {
        Log.i("ultimo",lastVisible.toString())
        if (lastVisible+1 >=BEER_FOR_PAGE) {

            val page = ((lastVisible+1) / BEER_FOR_PAGE )+1
            Log.i("ultimo oagina",page.toString())
            return repo.getBeersforPage(page)
        } else {
            return repo.getBeersforPage(1)
        }
    }
}