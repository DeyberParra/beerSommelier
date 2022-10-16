package com.deyber.beersommelier.ui.fragments
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentMainBinding
import com.deyber.beersommelier.ui.fragments.adapter.BeerPagerAdapter
import com.deyber.beersommelier.ui.fragments.adapter.BeerSearchAapter
import com.deyber.beersommelier.ui.vm.MainViewModel
import com.deyber.beersommelier.ui.vm.ShareDataViewModel
import com.deyber.beersommelier.ui.vm.PagingViewModel
import com.deyber.beersommelier.utils.enumTypes.SearchBeersTypes
import com.deyber.beersommelier.utils.extensions.onQueryTextChange
import com.deyber.beersommelier.utils.resource.TYPEERROR
import com.deyber.beersommelier.utils.resource.doFailure
import com.deyber.beersommelier.utils.resource.doLoading
import com.deyber.beersommelier.utils.resource.doSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private val vmPaging : PagingViewModel by viewModels()
    private val vmSearch :MainViewModel by viewModels()

    private val shareVm:ShareDataViewModel by activityViewModels()
    private lateinit var adapterPager : BeerPagerAdapter
    private lateinit var adapterSearch : BeerSearchAapter
    private lateinit var searchView :SearchView
    private lateinit var typeSearch :MenuItem
    private lateinit var searchBeersTypes:SearchBeersTypes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectState()
        adapterPager()
        adapterSearch()
        setHasOptionsMenu(true)

    }

    private fun observerSelectedTypeSearch(){
        shareVm.getSearchBeerType().observe(viewLifecycleOwner, Observer{ type->
            searchBeersTypes = type
            when(type){
                SearchBeersTypes.NAME_BEERS -> typeSearch.setIcon(R.drawable.beer)
                SearchBeersTypes.FOOD_BEERS -> typeSearch.setIcon(R.drawable.dinner)

            }
        })
    }
    private fun adapterSearch(){

        vmSearch.resultBeers().observe(viewLifecycleOwner, Observer{
            it.doLoading {
                binding.spinnerAnimation.visibility = View.VISIBLE
                binding.spinnerAnimation.playAnimation()
            }
            it.doSuccess { beers->
                binding.spinnerAnimation.pauseAnimation()
                binding.spinnerAnimation.visibility = View.GONE
                adapterSearch.sentData(beers , searchBeersTypes)
            }
            it.doFailure { error, throwable, typeError ->
                binding.spinnerAnimation.pauseAnimation()
                binding.spinnerAnimation.visibility = View.GONE
                if(typeError == TYPEERROR.NO_NETWORK){
                    findNavController().navigate(R.id.networkErrorFragment)
                }

            }
        })

    }
    private fun adapterPager(){
        adapterPager.addLoadStateListener { state->
            if(state.refresh is LoadState.Loading || state.append is LoadState.Loading){
                binding.spinnerAnimation.visibility = View.VISIBLE
            }else{
                binding.spinnerAnimation.visibility = View.GONE

                val error = when{
                    state.append is LoadState.Error -> state.append as LoadState.Error
                    state.prepend is LoadState.Error -> state.prepend as LoadState.Error
                    state.refresh is LoadState.Error -> state.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    findNavController().navigate(R.id.networkErrorFragment)
                }
            }
        }

    }

    private fun initView(){

        adapterPager = BeerPagerAdapter { beer ->
            shareVm.selectBeerDetail(beer)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }
        adapterSearch = BeerSearchAapter { beer->
            shareVm.selectBeerDetail(beer)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }
        binding.paginatorRv.adapter = adapterPager
        binding.paginatorRv.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.searchBeerRv.adapter = adapterSearch
        binding.searchBeerRv.layoutManager = GridLayoutManager(requireContext(),2)

    }

    private fun collectState(){

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                vmPaging.getBeers().collectLatest {
                    adapterPager.submitData(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        typeSearch = menu.findItem(R.id.type_search)
        searchView= searchItem.actionView as SearchView
        observerSelectedTypeSearch()
        searchView.onQueryTextChange { query->
            searchBeers(query)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.search_by_beer_name->{
                shareVm.selectSearchBeerType(SearchBeersTypes.NAME_BEERS)
                return true
            }
            R.id.search_by_food-> {
                shareVm.selectSearchBeerType(SearchBeersTypes.FOOD_BEERS)
                return true
            }
            else-> super.onOptionsItemSelected(item)

        }
    }

    private fun searchBeers(query:String){
        if(!query.isBlank()){
            vmSearch.searchData(searchBeersTypes, query)
            binding.searchBeerRv.visibility = View.VISIBLE
            binding.paginatorRv.visibility = View.INVISIBLE
        }else{
            binding.searchBeerRv.visibility = View.INVISIBLE
            binding.paginatorRv.visibility = View.VISIBLE
        }
    }
}