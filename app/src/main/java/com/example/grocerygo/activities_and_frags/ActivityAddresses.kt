package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Requester
import com.example.grocerygo.extras.easyToast
import com.example.grocerygo.models.Address
import com.example.grocerygo.models.received.ReceivedAddressesObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_addresses.*
import kotlinx.android.synthetic.main.item_address.view.*

class ActivityAddresses : GGToolbarActivity(R.layout.activity_addresses), AdapterRecyclerView.Callbacks {
    override val title = "Select Address"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
    }
    var addresses = arrayListOf<Address>()

    private fun setupRecyclerView() {

        Requester.requestAddresses(
            App.sm.user?._id,
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
            App.sm.primaryAddress = addresses[i]
        }
        view.button_trash.setOnClickListener {
            if (addresses[i] == App.sm.primaryAddress) {
                this.easyToast("Cannot delete primary address")
            } else {
                Requester.requestDeleteAddress(addresses[i]._id, Response.Listener { _ ->
                    //Once you're done deleting, update the addresses
                    Requester.requestAddresses(App.sm.user?._id,
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
    }

    override fun getRecyclerDataSize(): Int {
        return addresses.size
    }
}