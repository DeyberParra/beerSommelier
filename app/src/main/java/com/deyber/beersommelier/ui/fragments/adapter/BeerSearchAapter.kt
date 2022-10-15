package com.deyber.beersommelier.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deyber.beersommelier.R
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.databinding.ItemBeerBinding
import com.deyber.beersommelier.utils.constants.RetrofitConstants.defaultImg
import com.deyber.beersommelier.utils.enumTypes.SearchBeersTypes
import com.deyber.beersommelier.utils.extensions.picaso
import com.deyber.beersommelier.utils.extensions.toText


class BeerSearchAapter(private val listener:(item:BeerModel)->Unit):RecyclerView.Adapter<BeerSearchAapter.BeerViewHolder>() {

    private var beers:List<BeerModel> = listOf<BeerModel>()
    private lateinit var searchBeersTypes: SearchBeersTypes

    fun sentData(list:List<BeerModel>, searchBeersTypes: SearchBeersTypes){
        this.beers = list
        this.searchBeersTypes =searchBeersTypes
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer,parent,false)
        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {

        itemCount.let {
            var beer = beers[position]
            holder.itemView.setOnClickListener{
                listener(beer)
            }
            holder.bind(beer, searchBeersTypes)
        }
    }

    override fun getItemCount(): Int {
        return beers.size
    }

    class BeerViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val binding:ItemBeerBinding = ItemBeerBinding.bind(v)

        fun bind(item:BeerModel, searchBeersTypes: SearchBeersTypes){
            when(searchBeersTypes){
                SearchBeersTypes.NAME_BEERS ->{
                    binding.beerName.text = item.name
                    binding.beerFirstBrewed.text = item.firstBrewed
                    binding.beerImage.picaso(item.imageUrl?:defaultImg)
                }
                SearchBeersTypes.FOOD_BEERS -> {
                    binding.beerName.text = item.name
                    binding.beerFirstBrewed.text = item.foodPairing.toText()
                    binding.beerImage.picaso(item.imageUrl?:defaultImg)
                }
            }

        }

    }
}