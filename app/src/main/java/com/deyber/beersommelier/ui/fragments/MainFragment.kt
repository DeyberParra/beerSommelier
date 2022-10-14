package com.deyber.beersommelier.ui.fragments
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentMainBinding
import com.deyber.beersommelier.ui.fragments.adapter.BeerPagerAdapter
import com.deyber.beersommelier.ui.vm.DetailViewModel
import com.deyber.beersommelier.ui.vm.PagingViewModel
import com.deyber.beersommelier.utils.extensions.onQueryTextChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val vm: PagingViewModel by viewModels()
    private val detailVm:DetailViewModel by activityViewModels()
    private lateinit var adapter: BeerPagerAdapter
    private lateinit var searchView:SearchView
    private lateinit var typeSearch:MenuItem

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
        adapter.addLoadStateListener { state->
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
        setHasOptionsMenu(true)
    }

    private fun initView(){
        adapter = BeerPagerAdapter { beer ->
            detailVm.select(beer)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }
        binding.mainRyclerview.adapter = adapter
        binding.mainRyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
    }
    private fun collectState(){

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                vm.getBeers().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        typeSearch = menu.findItem(R.id.type_search)

        searchView= searchItem.actionView as SearchView
        searchView.onQueryTextChange {

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_search->{
                Log.i("click en lupa", "hiciste click en lupa")
                return true
            }
            R.id.search_by_beer_name->{
                typeSearch.setIcon(R.drawable.beer)
                return true
            }
            R.id.search_by_food-> {
                typeSearch.setIcon(R.drawable.dinner)
                return true
            }
            else-> super.onOptionsItemSelected(item)

        }
    }
}