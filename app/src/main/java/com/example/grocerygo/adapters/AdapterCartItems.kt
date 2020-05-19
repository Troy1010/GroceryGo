package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.FragCart
import com.example.grocerygo.inheritables.RecyclerViewActivityCallbacks
import com.example.grocerygo.models.Product

class AdapterCartItems(
    var parent: Fragment,
    var context: Context,
    var products: ArrayList<Product>
): RecyclerView.Adapter<AdapterCartItems.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCartItems.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: AdapterCartItems.ViewHolder, position: Int) {
        (parent as RecyclerViewActivityCallbacks).bindRecyclerItemView(holder.itemView, position)
    }

}