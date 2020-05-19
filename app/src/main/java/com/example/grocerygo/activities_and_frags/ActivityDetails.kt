package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.includible_plus_minus.*
import kotlinx.android.synthetic.main.includible_plus_minus.view.*

class ActivityDetails : GGToolbarActivity() {
    override val title: String
        get() = (intent.getSerializableExtra(KEY_PRODUCT) as Product).productName
    override val layout: Int
        get() = R.layout.activity_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val product = intent.getSerializableExtra(KEY_PRODUCT) as Product
        text_view_price.text = "$"+product.price.toString()
        text_view_details.text = product.description
        image_view.easyPicasso(Config.BASE_URL_ITEM_IMAGES + product.image)
        text_view_number_plus_minus.text = product.quantity.toString()
        if (product.quantity!=0) {
            text_view_add.visibility=View.GONE
        }
        text_view_add.setOnClickListener {
            text_view_add.visibility= View.GONE
            App.db.addProduct(product)
            text_view_number_plus_minus.text = product.quantity.toString()
        }
        button_plus.setOnClickListener {
            App.db.addProduct(product)
            text_view_number_plus_minus.text = product.quantity.toString()
        }
        button_minus.setOnClickListener {
            if (product.quantity == 1) {
                text_view_add.visibility= View.VISIBLE
                product.quantity = 0 // to update ui, probably unnecessary
                App.db.deleteProduct(product)
            } else {
                App.db.minusProduct(product)
            }
            text_view_number_plus_minus.text = product.quantity.toString()
        }
    }
}
