package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.extras.logz
import com.example.grocerygo.models.ProfileItem
import kotlinx.android.synthetic.main.item_profile.view.*

class AdapterProfile (var context: Context, var profileItems:ArrayList<ProfileItem>): RecyclerView.Adapter<AdapterProfile.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterProfile.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        logz("profileItems[position].title:${profileItems[position].title}")
        holder.itemView.text_view_title.text = profileItems[position].title
        holder.itemView.text_view_value.text = profileItems[position].value
    }
}