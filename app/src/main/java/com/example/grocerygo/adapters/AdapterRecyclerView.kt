package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.RecyclerViewActivityCallbacks

class AdapterRecyclerView(
    var binder: RecyclerViewActivityCallbacks,
    var context: Context
): RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return binder.getRecyclerDataSize()
    }

    override fun onBindViewHolder(holder: AdapterRecyclerView.ViewHolder, position: Int) {
        binder.bindRecyclerItemView(holder.itemView, position)
    }

}