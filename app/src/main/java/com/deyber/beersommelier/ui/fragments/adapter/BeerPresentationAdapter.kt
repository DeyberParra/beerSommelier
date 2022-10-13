package com.deyber.beersommelier.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deyber.beersommelier.R
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.databinding.ItemBeerBinding
import com.deyber.beersommelier.utils.constants.RetrofitConstants.defaultImg
import com.deyber.beersommelier.utils.extensions.picaso


class BeerPresentationAdapter(private val listener:(item:BeerModel)->Unit):RecyclerView.Adapter<BeerPresentationAdapter.BeerViewHolder>() {

    private var beers:List<BeerModel> = listOf<BeerModel>()

    fun sentData(list:List<BeerModel>){
        this.beers = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer,parent,false)
        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        itemCount.let { position->
            holder.bind(beers[position], position)
            holder.itemView.setOnClickListener{
                listener(beers[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if(beers.isNullOrEmpty()){
            0
        }else{
            beers.size
        }
    }

    class BeerViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val binding:ItemBeerBinding = ItemBeerBinding.bind(v)

        fun bind(item:BeerModel, position: Int){
            binding.beerName.text = item.name
            binding.beerFirstBrewed.text = item.firstBrewed
            binding.beerImage.picaso(item.imageUrl?:defaultImg)
        }

    }
}