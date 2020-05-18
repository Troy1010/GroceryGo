package com.example.grocerygo.activities_and_frags

import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_cart.*

class FragCart : TMFragment() {
    override val layout = R.layout.frag_cart
    val title = "Cart"

    override fun onStart() {
        super.onStart()
        val activityZ = activity
        if (activityZ is GGToolbarActivity) {
            activityZ.setToolbarTitle(this.title)
        }
        button_delete_first_item.setOnClickListener {
            App.db.deleteProductByIndex(0)
        }
        button_print_list.setOnClickListener {
            logz(App.db.getProducts().narrate())
        }
        button_update_first_item.setOnClickListener {
            var products = App.db.getProducts()
            val newProduct = Product(sqlID = products[0].sqlID, productName = "ERJOWIERJI")
            App.db.updateProduct(newProduct)

        }
    }


}
