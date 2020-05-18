package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterImageSlider
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.frag_home.*

class FragHome : Fragment() {
    val title = "Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, false)
        setupImageSlider()
        button_logout.setOnClickListener {
            App.sm.logout()
            startActivity(Intent(activity!!, ActivityLogin::class.java))
        }
//        Picasso
//                .get()
//                .load(Endpoints.HOME_IMAGE)
//                .placeholder(R.drawable.not_found)
//                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
//                .into(image_view)
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user.name)
    }

    private fun setupImageSlider() {
        val images = arrayListOf<Int>(R.drawable.grocery_basket_icons_bag_food_icon, R.drawable.slider_image_two)
        view_pager_image_slider.adapter = AdapterImageSlider(activity!!, images)
        view_pager_image_slider.setOnTouchListener { v, event ->
            true
        }
        // slide it on your own
        doInThree(Handler())
    }
    private fun doInThree(handler: Handler) {
        handler.postDelayed({
            view_pager_image_slider?.apply {
                view_pager_image_slider.currentItem += 1
                doInThree(handler)
            }
        },3000)
    }
}
