package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Title
import kotlinx.android.synthetic.main.frag_cart.*

class FragCart : Fragment(), Title {
    override val title = "Cart"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return inflater.inflate(R.layout.frag_cart, container, false)
    }

    private fun init() {
        button_delete_first_item.setOnClickListener {
            App.db.deleteProduct(0)
        }
    }


}
