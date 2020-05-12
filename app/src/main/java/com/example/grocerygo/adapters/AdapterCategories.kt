package com.example.grocerygo.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.ActivityProducts
import com.example.grocerygo.extras.Config
import com.example.grocerygo.extras.KEY_CAT_ID
import com.example.grocerygo.extras.KEY_SUB_TITLE
import com.example.grocerygo.models.Category
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
        var imagePath = Config.BASE_URL_ITEM_IMAGES + data[position].catImage
        if (data[position].catImage.isNotEmpty()) {
            Picasso
                .get()
                .load(imagePath)
                .placeholder(R.drawable.not_found)
                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
                .into(holder.itemView.image_view)
        }
        // click listener
        holder.itemView.setOnClickListener {
            var intent = Intent(context, ActivityProducts::class.java)
            intent.putExtra(KEY_CAT_ID, position)
            intent.putExtra(KEY_SUB_TITLE, data[position].catName)
            context.startActivity(intent)
        }
    }
}