package com.example.grocerygo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.FragSearchToolbar
import com.example.grocerygo.extras.Config
import com.example.grocerygo.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_category.view.image_view

class AdapterCategories(
    var activity: FragmentActivity,
    var categories: ArrayList<Category>
) : RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_view.text = categories[position].catName
        // Picasso image_view
        var imagePath = Config.BASE_URL_ITEM_IMAGES + categories[position].catImage
        if (categories[position].catImage.isNotEmpty()) {
            Picasso
                .get()
                .load(imagePath)
                .placeholder(R.drawable.not_found)
                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
                .into(holder.itemView.image_view)
        }
        // click listener
        holder.itemView.setOnClickListener {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.frame_fragments, FragSearchToolbar.newInstance(categories[position].catId))
                .addToBackStack(null) // is null okay?
                .commit()
        }
    }
}