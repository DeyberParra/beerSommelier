package com.deyber.beersommelier.domain

import com.deyber.beersommelier.data.network.Repository.Repository
import com.deyber.beersommelier.data.network.Repository.RepositoryImplementation
import com.deyber.beersommelier.data.network.model.BeerModel
import javax.inject.Inject

class BeerNetworkUseCase @Inject constructor(private val repo: RepositoryImplementation):Repository {
    override suspend fun getBeers(): List<BeerModel>? {
        return repo.getBeers()
    }
}