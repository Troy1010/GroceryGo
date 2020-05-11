package com.example.grocerygo.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.grocerygo.activities_and_frags.FragProducts
import com.example.grocerygo.models.SubCategory

class AdapterSubCategories(var fm: FragmentManager):FragmentPagerAdapter(fm) {
    var data =  arrayListOf<SubCategory>()
        set(value) {
            // debug
            Log.d("TMLog","AdapterSubCategories`dataSetter`data`value:$value")
            var s = ""
            for (item in value) {
                s += item.subName +"("+item.subId+")"+ ","
            }
            Log.d("TMLog",s.dropLast(1))
            //
            field = value
            notifyDataSetChanged()
        }
    override fun getItem(position: Int): Fragment {
        Log.d("TMLog","AdapterSubCategories`getItem`position:$position subId:${data[position].subId}")
        return FragProducts.newInstance(data[position].subId)
    }

    override fun getCount(): Int {
        Log.d("TMLog","AdapterSubCategories`getCount`data.size:${data.size}")
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        Log.d("TMLog","AdapterSubCategories`getPageTitle`title:${data[position].subName}")
        return data[position].subName
    }
}