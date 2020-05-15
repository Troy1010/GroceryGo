package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.extras.Title

class FragCart : Fragment() , Title {
    override val title = "Cart"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_cart, container, false)
    }

    
}
