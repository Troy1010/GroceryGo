package com.example.grocerygo_jsonparsingandconfig.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocerygo_jsonparsingandconfig.R
import com.example.grocerygo_jsonparsingandconfig.extras.PRODUCT_KEY
import com.example.grocerygo_jsonparsingandconfig.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.row_category.view.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }

    private fun init() {
        val product = intent.getSerializableExtra(PRODUCT_KEY) as Product

        text_view_name.text = product.productName
        text_view_price.text = "$"+product.price.toString()
        text_view_details.text = product.description

        var imagePath = "http://rjtmobile.com/grocery/images/" + product.image
        if (imagePath.isNotEmpty()) {
            Picasso
                .get()
                .load(imagePath)
                .placeholder(R.drawable.not_found)
                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
                .into(image_view)
        }
    }
}
