package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.extras.Config
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.KEY_PRODUCT
import com.example.grocerygo.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }

    private fun init() {
        val product = intent.getSerializableExtra(KEY_PRODUCT) as Product

        text_view_name.text = product.productName
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
        setupToolbar(product.productName)
    }

    private fun setupToolbar(title:String) { // TODO refactor this out
        toolbar_top.title = title
        setSupportActionBar(toolbar_top)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
