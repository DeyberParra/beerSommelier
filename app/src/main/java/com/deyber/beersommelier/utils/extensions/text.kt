package com.deyber.beersommelier.utils.extensions

import android.widget.LinearLayout
import android.widget.TextView
import com.deyber.beersommelier.data.network.model.Hops
import com.deyber.beersommelier.data.network.model.Malt
import com.deyber.beersommelier.data.network.model.MashTemp
import com.deyber.beersommelier.utils.customViews.CustomListView

fun <R>TextView.listToString(data:ArrayList<R>){
    var str:String =""
    data.map{
       str+= "$it\r\n"
    }
    this.text =str
}

fun <R>LinearLayout.addElement(data:ArrayList<R>){
    data.map{
        val vf = CustomListView(this.context)
        vf.setTitle(it.toString())
        this.addView(vf)
    }
}
fun TextView.mashTemp(data:ArrayList<MashTemp>){
    var str:String =""
    data.map{
        str+="${it.temp?.value} ${it.temp?.unit}\r\n"
    }
    this.text =str
}
fun TextView.malt(data:ArrayList<Malt>){
    var str:String =""
    data.map{
        str+="${it.name} ${it.amount?.value} ${it.amount?.unit}\r\n"
    }
    this.text =str
}

fun TextView.hops(data:ArrayList<Hops>){
        var str:String =""
        data.map{
            str+="${it.name} ${it.attribute} ${it.amount?.value} ${it.amount?.unit} ${it.add}\r\n"
        }
    this.text =str
}