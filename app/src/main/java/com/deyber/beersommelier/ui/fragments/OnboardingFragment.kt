package com.deyber.beersommelier.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.FragmentOnboardingBinding
import com.deyber.beersommelier.di.userDataStore
import com.deyber.beersommelier.ui.fragments.adapter.SlideAdapter
import com.deyber.beersommelier.utils.constants.DataStoreConstants.ONBOARDING_KEY
import com.deyber.beersommelier.utils.dataclass.Slide
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.prefs.Preferences
import javax.inject.Inject

class OnboardingFragment : Fragment() {

    private lateinit var binding:FragmentOnboardingBinding
    @Inject lateinit var prefs: DataStore<Preferences>

    private lateinit var adapter:SlideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            getOnboardingState().collect { state->
               if(state)
                   findNavController().navigate(R.id.action_onboardingFragment_to_mainFragment)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getOnboardingState() = requireContext().userDataStore.data.map{
        preference->
        preference[booleanPreferencesKey(ONBOARDING_KEY)]?:false
    }
    suspend fun saveOnboardingState(newValue:Boolean){
        requireContext().userDataStore.edit {
            preferences->
            preferences[booleanPreferencesKey(ONBOARDING_KEY)]=newValue
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SlideAdapter(listOf(
            Slide(R.drawable.screen1,
                getString(R.string.slide_first_title),
                getString(R.string.slide_first_content)),
            Slide(R.drawable.screen2,
                getString(R.string.slide_second_title),
                getString(R.string.slide_second_content),),
            Slide(R.drawable.screen3,
                getString(R.string.slide_thirt_title),
                getString(R.string.slide_thirt_content)))
        )

        binding.viewPager.adapter = adapter
        configViewPager()

        binding.buttonNext.setOnClickListener{
            lifecycleScope.launch {
                saveOnboardingState(true)
            }
        }
    }

    private fun configViewPager(){

        binding.indicator.setViewPager(binding.viewPager)
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (position == adapter.itemCount - 1) {
                        val animation = AnimationUtils.loadAnimation(
                            requireActivity(),
                            R.anim.app_name_animation
                        )
                        binding.buttonNext.animation = animation
                        binding.buttonNext.text = getString(R.string.Finis)
                        binding.buttonNext.setOnClickListener {
                            lifecycleScope.launch {
                                saveOnboardingState(true)
                            }
                        }
                    } else {
                        binding.buttonNext.text = getString(R.string.Next)
                        binding.buttonNext.setOnClickListener {
                            binding.viewPager.currentItem.let {
                                binding.viewPager.setCurrentItem(it + 1, false)
                            }
                        }
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}