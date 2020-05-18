package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class ActivityDetails : GGToolbarActivity() {
    override val title: String
        get() = (intent.getSerializableExtra(KEY_PRODUCT) as Product).productName
    override val layout: Int
        get() = R.layout.activity_details

    override fun onCreate(savedInstanceState: Bundle?) {
        val product = intent.getSerializableExtra(KEY_PRODUCT) as Product
        super.onCreate(savedInstanceState)
        text_view_price.text = "$"+product.price.toString()
        text_view_details.text = product.description
        var imagePath = Config.BASE_URL_ITEM_IMAGES + product.image
        if (imagePath.isNotEmpty()) {
            Picasso
                    .get()
                    .load(imagePath)
                    .placeholder(R.drawable.not_found)
                    .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
                    .into(image_view)
        }
        button_add_to_cart.setOnClickListener {
            this.easyToast("${product.productName} added to cart")

            App.db.addProduct(product)
        }
    }
}
