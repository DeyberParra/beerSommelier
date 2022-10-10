package com.deyber.beersommelier.utils.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.picaso(src:String){
    Picasso.get().load(src).into(this);
}