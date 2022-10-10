package com.deyber.beersommelier.utils.customViews

import android.widget.LinearLayout
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.deyber.beersommelier.R

class FoodView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val title:TextView

    init {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.view_food, this, true)
        title = view.findViewById(R.id.food_title)

    }

    fun setTitle(data:String){
        title.text = data
    }
}