package com.deyber.beersommelier.ui.fragments
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentMainBinding
import com.deyber.beersommelier.ui.fragments.adapter.BeerPresentationAdapter
import com.deyber.beersommelier.ui.vm.DetailViewModel
import com.deyber.beersommelier.ui.vm.MainViewModel
import com.deyber.beersommelier.utils.resource.TYPEERROR
import com.deyber.beersommelier.utils.resource.doFailure
import com.deyber.beersommelier.utils.resource.doLoading
import com.deyber.beersommelier.utils.resource.doSuccess
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainVm: MainViewModel by viewModels()
    private val detailVm:DetailViewModel by activityViewModels()
    private lateinit var adapter: BeerPresentationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BeerPresentationAdapter { beer ->
            detailVm.select(beer)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }
        binding.mainRyclerview.adapter = adapter
        binding.mainRyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        mainVm.onCreate()
        mainVm.getBeers().observe(viewLifecycleOwner, Observer {
            it.doLoading {
                Log.i("beers", "cargando datos...")
            }
            it.doSuccess { data ->
                Log.i("beers", data.toString())
                adapter.sentData(data)
            }
            it.doFailure { error, throwable, typeError ->
                when (typeError) {
                    TYPEERROR.NO_NETWORK -> findNavController().navigate(R.id.networkErrorFragment)
                    TYPEERROR.NO_DATA -> findNavController().navigate(R.id.networkErrorFragment)
                }
            }
        })
    }
}