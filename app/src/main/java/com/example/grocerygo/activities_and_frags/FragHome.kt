package com.example.grocerygo.activities_and_frags

import android.os.Handler
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.adapters.AdapterImageSlider
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import kotlinx.android.synthetic.main.frag_home.*

class FragHome : TMFragment(layout = R.layout.frag_home) {



    override fun onStart() {
        super.onStart()
        setupParent()
        setupImageSlider()
//        button_logout.setOnClickListener {
//            App.sm.logout()
//            (activity as HostCallbacks).goToProfile()
//        }
//        Picasso
//                .get()
//                .load(Endpoints.HOME_IMAGE)
//                .placeholder(R.drawable.not_found)
//                .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
//                .into(image_view)
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user.name?:"Welcome!")
    }



    private fun setupParent() {
        (activity as HostCallbacks).showNavigationBar(true)
        (activity as ToolbarCallbacks).showCart(true)
        (activity as ToolbarCallbacks).showBack(false)
        (activity as ToolbarCallbacks).setTitle("Home")
    }

    private fun setupImageSlider() {
        val images = arrayListOf<Int>(R.drawable.grocery_basket_icons_bag_food_icon, R.drawable.slider_image_two)
        view_pager_image_slider.adapter = AdapterImageSlider(activity!!, images)
        view_pager_image_slider.setOnTouchListener { _, _ ->
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
