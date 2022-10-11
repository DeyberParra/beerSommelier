package com.deyber.beersommelier.ui.fragments.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.deyber.beersommelier.ui.fragments.detailFragments.AbstractFragment
import com.deyber.beersommelier.ui.fragments.detailFragments.FoodParingFragment
import com.deyber.beersommelier.ui.fragments.detailFragments.IngredientsFragment


class DetailPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    companion object{
        const val options:Int = 3
        const val option_1:String = "Food paring"
        const val option_2:String = "Ingredients"
        const val option_3:String = "Abstract"
    }

    override fun getCount(): Int {
        return options
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FoodParingFragment()
            1 -> return IngredientsFragment()
            2 -> return AbstractFragment()
            else-> return AbstractFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return option_1
            1 -> return option_2
            2 -> return option_3
            else-> return option_1
        }
    }
}