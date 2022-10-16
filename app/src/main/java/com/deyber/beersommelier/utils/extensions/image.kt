package com.deyber.beersommelier.utils.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.deyber.beersommelier.R
import com.squareup.picasso.Picasso

fun ImageView.picaso(src:String){
    Picasso.get().load(src).into(this);
}

fun ImageView.setDrawableImage(image:Int ){
    this.setImageResource(image)
}