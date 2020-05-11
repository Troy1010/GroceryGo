package com.example.grocerygo_jsonparsingandconfig.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo_jsonparsingandconfig.R
import com.example.grocerygo_jsonparsingandconfig.activities_and_frags.DetailsActivity
import com.example.grocerygo_jsonparsingandconfig.extras.PRODUCT_KEY
import com.example.grocerygo_jsonparsingandconfig.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category.view.image_view
import kotlinx.android.synthetic.main.row_product.view.*

class AdapterProducts (var context: Context): RecyclerView.Adapter<AdapterProducts.ViewHolder>() {
    var data =  arrayListOf<Product>(Product(productName = "NAME"))
        set(value) {
            Log.d("TMLog","AdapterProducts`dataSetter`data`value:$value")
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_view_name.text = data[position].productName
        holder.itemView.text_view_price.text = "$"+data[position].price.toString()
        holder.itemView.text_view_fake_price.text = "$"+data[position].mrp.toString()
        holder.itemView.text_view_fake_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        // Picasso image_view
        var imagePath = "http://rjtmobile.com/grocery/images/" + data[position].image
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
            var intent = Intent(context, DetailsActivity::class.java)
            var product = data[position]
            intent.putExtra(PRODUCT_KEY, product)
            context.startActivity(intent)
        }
    }
}