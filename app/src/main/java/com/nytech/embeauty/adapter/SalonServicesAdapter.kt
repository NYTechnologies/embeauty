package com.nytech.embeauty.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nytech.embeauty.R
import com.nytech.embeauty.model.SalonModel

/**
 * Adapter para receber a lista de Services do backend e renderizar na tela do SalonServicesFragment
 */
class SalonServicesAdapter(
   private val context: Context,
   private val salonServices: List<SalonModel.Service>
) : ArrayAdapter<SalonModel.Service>(context, R.layout.services_list_item, salonServices) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.services_list_item, null)

        val serviceName: TextView = view.findViewById(R.id.salonServiceName)
        val servicePrice: TextView = view.findViewById(R.id.salonServicePrice)
        
        serviceName.text = salonServices[position].name
        servicePrice.text = salonServices[position].price

        return view
    }

}







