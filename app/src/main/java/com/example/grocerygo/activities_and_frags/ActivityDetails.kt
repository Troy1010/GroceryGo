package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // TODO refactor
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // TODO refactor
        when (item.itemId) {
            android.R.id.home->{
                finish()
            }
            R.id.menu_cart -> {
                Log.d("TMLog","OptionsMenu`Selected Cart")
            }
            R.id.menu_profile -> {
                Log.d("TMLog","OptionsMenu`Selected Profile")
            }
            R.id.menu_settings -> {
                Log.d("TMLog","OptionsMenu`Selected Settings")
            }
        }
        return true
    }
}
