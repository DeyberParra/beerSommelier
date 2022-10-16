package com.deyber.beersommelier.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deyber.beersommelier.databinding.ItemOnboardingSlideBinding
import com.deyber.beersommelier.utils.dataclass.Slide
import com.deyber.beersommelier.utils.extensions.setDrawableImage

class SlideAdapter(private val introSlides: List<Slide>)
    : RecyclerView.Adapter<SlideAdapter.SlideViewHolder>(){


    var onTextPassed: ((textView: TextView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        return SlideViewHolder(
            ItemOnboardingSlideBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    inner class SlideViewHolder(private val binding:ItemOnboardingSlideBinding ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Slide) {
            binding.textTitle.text = data.title
            binding.textDescription.text = data.description
            binding.imageSlide.setDrawableImage(data.image)
            onTextPassed?.invoke(binding.textTitle)
        }
    }
}