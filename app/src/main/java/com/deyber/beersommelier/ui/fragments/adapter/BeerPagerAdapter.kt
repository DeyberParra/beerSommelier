package com.deyber.beersommelier.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deyber.beersommelier.R
import com.deyber.beersommelier.data.network.model.BeerModel
import com.deyber.beersommelier.databinding.ItemBeerBinding
import com.deyber.beersommelier.utils.constants.RetrofitConstants.defaultImg
import com.deyber.beersommelier.utils.extensions.picaso


class BeerPagerAdapter(private val listener:(item:BeerModel)->Unit):PagingDataAdapter<BeerModel, BeerPagerAdapter.BeerPagerViewHolder>(BeerDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer,parent,false)
        return BeerPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerPagerViewHolder, position: Int) {
        getItem(position)?.let { beerModel->
            holder.bind(beerModel)
            holder.itemView.setOnClickListener{
                listener(beerModel)
            }
        }
    }

    class BeerDiffCallBack : DiffUtil.ItemCallback<BeerModel>() {
        override fun areItemsTheSame(oldItem: BeerModel, newItem: BeerModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: BeerModel, newItem: BeerModel): Boolean {
            return oldItem == newItem
        }
    }

    class BeerPagerViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val binding:ItemBeerBinding = ItemBeerBinding.bind(v)

        fun bind(item:BeerModel){
            binding.beerName.text = item.name
            binding.beerFirstBrewed.text = item.firstBrewed
            binding.beerImage.picaso(item.imageUrl?:defaultImg)
        }

    }
}