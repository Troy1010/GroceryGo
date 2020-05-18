package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.FragCart
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.item_cart_item.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.text_view_name
import kotlinx.android.synthetic.main.item_product.view.text_view_price

class AdapterCartItems(
    var parent: FragCart,
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
        holder.itemView.text_view_name.text = products[position].productName
        holder.itemView.text_view_price.text = "$"+products[position].price.toString()
        holder.itemView.text_view_quantity.text = "Quantity: "+products[position].quantity.toString()
        holder.itemView.button_trash.setOnClickListener {
            parent.deleteProduct(products[position])
        }
    }

}