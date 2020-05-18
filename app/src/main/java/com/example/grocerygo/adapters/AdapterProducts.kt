package com.example.grocerygo.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.ActivityDetails
import com.example.grocerygo.extras.KEY_PRODUCT
import com.example.grocerygo.inheritables.TMAdapter
import com.example.grocerygo.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category.view.image_view
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterProducts (override val context: Context, var products:ArrayList<Product>): TMAdapter() {
    override val layout: Int
        get() = R.layout.item_product

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_view_name.text = products[position].productName
        holder.itemView.text_view_price.text = "$"+products[position].price.toString()
        holder.itemView.text_view_fake_price.text = "$"+products[position].mrp.toString()
        holder.itemView.text_view_fake_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        // Picasso image_view
        var imagePath = "http://rjtmobile.com/grocery/images/" + products[position].image
        if (imagePath.isNotEmpty()) {
            Picasso
                .get()
                .load(imagePath)
                .placeholder(R.drawable.not_found)
                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
                .into(holder.itemView.image_view)
        }
        //
        holder.itemView.setOnClickListener {
            var intent = Intent(context, ActivityDetails::class.java)
            var product = products[position]
            intent.putExtra(KEY_PRODUCT, product)
            context.startActivity(intent)
        }
    }
}