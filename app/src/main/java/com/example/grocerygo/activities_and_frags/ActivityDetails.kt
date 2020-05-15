package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityDetails : AppCompatActivityWithToolbarFunctionality() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }

    private fun init() {
        val product = intent.getSerializableExtra(KEY_PRODUCT) as Product

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
        toolbar_top.setup(this, product.productName)



        button_add_to_cart.setOnClickListener {
            this.easyToast("${product.productName} added to cart")
            var db = DBHelper()
            db.addProduct(product.productName,"1") // TODO amount shouldn't be literal
        }
    }
}
