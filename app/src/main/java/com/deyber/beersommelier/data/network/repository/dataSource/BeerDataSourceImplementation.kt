package com.deyber.beersommelier.data.network.repository.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.deyber.beersommelier.data.network.repository.dataSource.BeersPagingSource.Companion.NETWORK_PAGE_SIZE
import com.deyber.beersommelier.data.network.model.BeerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeerDataSourceImplementation @Inject constructor(val pagingSource: BeersPagingSource) {

    fun getBeers(): Flow<PagingData<BeerModel>>{
        return Pager(
            config =
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false),
            pagingSourceFactory = {pagingSource}
        ).flow
    }
}