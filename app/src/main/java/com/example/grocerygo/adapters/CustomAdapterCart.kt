package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.extras.logz
import com.example.grocerygo.models.Product


class CustomAdapterCart(
    var parent: Callbacks,
    var context: Context
) : RecyclerView.Adapter<CustomAdapterCart.ViewHolder>() {
    lateinit var zsfsParent : ViewGroup
    var zsfsHeight : Int = -1
    lateinit var oldView : View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return if (position==parent.products.size) {
            ItemViewTypeEnums.last.typeID
        } else {
            ItemViewTypeEnums.normalItem.typeID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterCart.ViewHolder {
        zsfsParent = parent
        val view = if (viewType == ItemViewTypeEnums.last.typeID) {
            LayoutInflater.from(context).inflate(R.layout.z_cart_last_item, parent, false)
        } else {
            oldView = LayoutInflater.from(context).inflate(R.layout.item_cart_item, parent, false)
            oldView
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parent.products.size + 1
    }

    override fun onBindViewHolder(holder: CustomAdapterCart.ViewHolder, position: Int) {
        if (position < parent.products.size) {
            parent.bindRecyclerItemView(holder.itemView, position)
            zsfsHeight = holder.itemView.height
            holder.itemView
        } else {
            parent.bindLastRecyclerItemView(holder.itemView, oldView.height)
        }
    }



    interface Callbacks
    {
        var products: ArrayList<Product>
        fun bindRecyclerItemView(view: View, i: Int)
        fun bindLastRecyclerItemView(view: View, normalItemHeight: Int)
    }
    enum class ItemViewTypeEnums(val typeID: Int) {
        normalItem(0), last(1)
    }
}

