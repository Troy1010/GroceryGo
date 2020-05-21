package com.example.grocerygo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.CartRecyclerViewCallbacks


class AdapterCart(
    var parent: CartRecyclerViewCallbacks,
    var context: Context
) : RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    lateinit var zsfsParent : ViewGroup
    var zsfsHeight : Int = -1
    lateinit var oldView : View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return if (position==parent.products.size) {
            logz("position:$position type:${ItemViewTypeEnums.last.typeID}")
            ItemViewTypeEnums.last.typeID
        } else {
            logz("position:$position type:${ItemViewTypeEnums.normalItem.typeID}")
            ItemViewTypeEnums.normalItem.typeID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCart.ViewHolder {
        zsfsParent = parent
        logz("*******************parent.height:${parent.height}")
        val view = if (viewType == ItemViewTypeEnums.last.typeID) {
            LayoutInflater.from(context).inflate(R.layout.z_cart_last_item, parent, false)
        } else {
            oldView = LayoutInflater.from(context).inflate(R.layout.item_cart_item, parent, false)
            oldView
        }
        logz("view.heighttttttttttttttt:${view.height}")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parent.products.size + 1
    }

    override fun onBindViewHolder(holder: AdapterCart.ViewHolder, position: Int) {
        logz("*****parent.height:${zsfsParent.height}")
//        logz("*****parent:${parent.}")
        if (position < parent.products.size) {
            logz("onBindViewHolder`position:$position < parent.products.size:${parent.products.size}")
            parent.bindRecyclerItemView(holder.itemView, position)
            zsfsHeight = holder.itemView.height
            holder.itemView
            logz("zsfsHeight:$zsfsHeight")
        } else {
            logz("oldView.heightzzzzzzzzz:${oldView.height}")
//            zsfsParent.layoutMode
//            zsfsParent.setLayoutParams(ConstraintLayout.LayoutParams(200,300))
            logz("onBindViewHolder`position:$position >= parent.products.size:${parent.products.size}")
            parent.bindLastRecyclerItemView(holder.itemView, oldView.height)
        }
    }
}

enum class ItemViewTypeEnums(val typeID: Int) {
    normalItem(0), last(1)
}
