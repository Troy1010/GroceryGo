package com.example.grocerygo_jsonparsingandconfig.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo_jsonparsingandconfig.R
import com.example.grocerygo_jsonparsingandconfig.activities_and_frags.SecondActivity
import com.example.grocerygo_jsonparsingandconfig.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category.view.*
import kotlinx.android.synthetic.main.row_category.view.image_view

class AdapterCategories (var context: Context): RecyclerView.Adapter<AdapterCategories.ViewHolder>() {
    var data =  arrayListOf<Category>(Category())
        set(value) {
            Log.d("TMLog","RecyclerView`dataSetter`data`value:$value")
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_view.text = data[position].catName
        // Picasso image_view
//        Log.d("TMLog","data[position].catImage:${data[position].catImage}")
        var imagePath = "http://rjtmobile.com/grocery/images/" + data[position].catImage
        Log.d("TMLog","imagepath:$imagePath")
        if (data[position].catImage.isNotEmpty()) {
            Picasso
                .get()
                .load(imagePath)
                .placeholder(R.drawable.not_found)
                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
                .into(holder.itemView.image_view)
        }
        // link click listener
        holder.itemView.setOnClickListener {
            var intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("CATEGORYINDEX", position)
            context.startActivity(intent)
        }
    }
}