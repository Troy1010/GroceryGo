package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Requester
import com.example.grocerygo.extras.easyToast
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.*
import com.example.grocerygo.models.received.ReceivedAddressesObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_c_payment_info.*
import kotlinx.android.synthetic.main.item_address.view.*

class ActivityPaymentInfo: GGToolbarActivity(), AdapterRecyclerView.Callbacks {
    override val title: String
        get() = "Payment Info"
    override val layout: Int
        get() = R.layout.activity_c_payment_info

    var addresses = arrayListOf<Address>()

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        refreshNonRecyclerViews()
    }

    private fun setupListeners() {
        button_payment_info_send.setOnClickListener {
            startActivity(Intent(this, ActivityOrderReview::class.java))
        }
        frame_address.setOnClickListener {
            startActivity(Intent(this, ActivityAddress::class.java))
        }
        frame_payment.setOnClickListener {
            startActivity(Intent(this, ActivityPayment::class.java))
        }
    }

    fun refreshNonRecyclerViews() {
        text_view_address_value.text = App.sm.user.primaryAddress?.houseNo + " " + App.sm.user.primaryAddress?.streetName
    }

    private fun setupRecyclerView() {
        Requester.requestAddresses(App.sm.user._id,
            Response.Listener { response ->
                val receivedAddressesObject = GsonBuilder().create()
                    .fromJson(response.toString(), ReceivedAddressesObject::class.java)
                //
                addresses = ArrayList(receivedAddressesObject.data)
                recycler_view_addresses.adapter?.notifyDataSetChanged()
            })
        recycler_view_addresses.layoutManager = LinearLayoutManager(this)
        recycler_view_addresses.adapter = AdapterRecyclerView(this, this, R.layout.item_address)
        recycler_view_addresses
            .addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view_address.text = addresses[i].streetName
        view.text_view_address.setOnClickListener {
            App.sm.user = User(App.sm.user.name, App.sm.user.email, App.sm.user.password, App.sm.user.mobile, addresses[i], App.sm.user._id)//TODO this could be simplified
            refreshNonRecyclerViews()
        }
        view.button_trash.setOnClickListener {
            if (addresses[i] == App.sm.user.primaryAddress) {
                this.easyToast("Cannot delete primary address")
            } else {
                Requester.requestDeleteAddress(addresses[i]._id, Response.Listener { _ ->
                    //Once you're done deleting, update the addresses
                    Requester.requestAddresses(App.sm.user._id,
                        Response.Listener { response2 ->
                        val receivedAddressesObject = GsonBuilder().create()
                            .fromJson(response2.toString(), ReceivedAddressesObject::class.java)
                        //
                        addresses = ArrayList(receivedAddressesObject.data)
                        recycler_view_addresses.adapter?.notifyDataSetChanged()
                    })
                })
            }
        }
//        view.
    }

    override fun getRecyclerDataSize(): Int {
        return addresses.size
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    // Setup Toolbar
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_cart).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }
}