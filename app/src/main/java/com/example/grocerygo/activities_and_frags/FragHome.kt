package com.example.grocerygo.activities_and_frags

import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.grocerygo.adapters.AdapterImageSlider
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.frag_home.*

class FragHome : TMFragment(layout = R.layout.frag_home) {



    override fun onStart() {
        super.onStart()
        setupParent()
        setupImageSlider()
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user?.name?:"Welcome!")
    }



    private fun setupParent() {
        (activity as HostCallbacks).showNavigationBar(true)
        (activity as ToolbarCallbacks).showCart(true)
        (activity as ToolbarCallbacks).showBack(false)
        (activity as ToolbarCallbacks).setTitle("Home")
    }

    private fun setupImageSlider() {
        val images = arrayListOf<Int>(
            R.drawable.si_fruitsandveggies_custom,
            R.drawable.si_grocery_isle,
            R.drawable.si_stock_veggies,
            R.drawable.si_supermarketcart_main
        )
        slider_view.setSliderAdapter(AdapterImageSlider(activity!!, images))
        slider_view.startAutoCycle()
        slider_view.setIndicatorAnimation(IndicatorAnimations.WORM)
        slider_view.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
    }
}
