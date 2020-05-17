package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.grocerygo.R
import kotlinx.android.synthetic.main.item_image_slider.view.*


class AdapterImageSlider(var context: Context, var imageLayouts: ArrayList<Int>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image_slider, container, false)
        view.image_view_of_slider.setImageResource(imageLayouts[getVirtualPosition(position)])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViewAt(getVirtualPosition(position))
    }

    private fun getVirtualPosition(position:Int):Int {
        return position % imageLayouts.size
    }



}
