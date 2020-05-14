package com.example.grocerygo.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.grocerygo.activities_and_frags.FragProducts
import com.example.grocerygo.extras.logz
import com.example.grocerygo.models.SubCategory

class AdapterSubCategories(var fm: FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var data =  arrayListOf<SubCategory>()
        set(value) {
            // debug
            var s = ""
            for (item in value) {
                s += item.subName +"("+item.subId+")"+ ","
            }
            logz("AdapterSubCategories`"+s.dropLast(1))
            //
            field = value
            notifyDataSetChanged()
        }
    override fun getItem(position: Int): Fragment {
        return FragProducts.newInstance(data[position].subId)
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].subName
    }
}