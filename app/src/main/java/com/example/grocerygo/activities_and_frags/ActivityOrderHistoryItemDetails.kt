package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.models.Product
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.activity_order_history_item_details.*

class ActivityOrderHistoryItemDetails(): GGToolbarActivity(R.layout.activity_order_history_item_details) {
    override val title = "Past Order"
    companion object {
        const val KEY_PRODUCTS = "PRODUCTS"
        const val KEY_DATE = "DATE"
    }
    val products by lazy {
        var returning = intent?.getSerializableExtra(KEY_PRODUCTS)
        if (returning==null) {
            logz("ActivityOrderHistoryItemDetails`could not receive Products")
            returning = ArrayList<Product>()
        }
        returning as ArrayList<Product>
    }
    val date by lazy {
        intent?.getStringExtra(KEY_DATE)?:""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frag = FragOrderReview.newInstance(products)
        supportFragmentManager.beginTransaction().replace(R.id.frame_frag_order_review, frag).commit()
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        text_view_date_value.text = date.take(10)
    }
}