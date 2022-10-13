package com.deyber.beersommelier.data.network.repository.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.deyber.beersommelier.data.network.BeerClient
import com.deyber.beersommelier.data.network.model.BeerModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BeersPagingSource @Inject constructor(private val api:BeerClient):PagingSource<Int, BeerModel>(){
   companion object{
       private const val DEFAULT_PAGE_INDEX = 1
       const val NETWORK_PAGE_SIZE = 25
   }

    override fun getRefreshKey(state: PagingState<Int, BeerModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerModel> {

        val pageIndex = params.key ?: DEFAULT_PAGE_INDEX
        return try{
            val response = api.getBeersforPage(pageIndex)
            val beers = response?.body()?: emptyList()
            val nextKey =
                if(beers.isEmpty()){
                    null
                }else{
                    pageIndex+(params.loadSize/ NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = beers,
                prevKey = if(pageIndex == DEFAULT_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        }catch (exception:IOException){
            return LoadResult.Error(exception)
        }catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}


