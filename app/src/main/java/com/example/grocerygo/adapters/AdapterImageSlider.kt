package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocerygo.R
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_image_slider.view.*


class AdapterImageSlider(val context: Context, val images: ArrayList<Int>) : SliderViewAdapter<AdapterImageSlider.SliderAdapterVH>() {
    inner class SliderAdapterVH(itemView: View) : SliderViewAdapter.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val v = LayoutInflater.from(context).inflate(R.layout.item_image_slider, null)
        return SliderAdapterVH(v)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        viewHolder.itemView.image_view_of_slider.setImageResource(images[position%images.size])
    }
}