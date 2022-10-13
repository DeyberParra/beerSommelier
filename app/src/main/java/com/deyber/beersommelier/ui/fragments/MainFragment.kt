package com.deyber.beersommelier.ui.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentMainBinding
import com.deyber.beersommelier.ui.fragments.adapter.BeerPagerAdapter
import com.deyber.beersommelier.ui.vm.DetailViewModel
import com.deyber.beersommelier.ui.vm.PagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val vm: PagingViewModel by viewModels()
    private val detailVm:DetailViewModel by activityViewModels()
    private lateinit var adapter: BeerPagerAdapter

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

}