package com.nytech.embeauty.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.nytech.embeauty.R
import com.nytech.embeauty.UpdateServiceActivity
import com.nytech.embeauty.constants.IntentConstants
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

        // Lógica do botão de editar o serviço
        val editServiceButton = view.findViewById<ImageButton>(R.id.salonServiceEditButton)
        editServiceButton.setOnClickListener {

            // Criar a intenção (Intent) para a nova atividade
            val intent = Intent(context, UpdateServiceActivity::class.java)
            Log.d("Antes de colocar no Intent: ", "${serviceName.text}+${servicePrice.text}")

            intent.putExtra(IntentConstants.OLD_SERVICE_NAME, serviceName.text.toString())
            intent.putExtra(IntentConstants.OLD_SERVICE_PRICE, servicePrice.text.toString())

            // Iniciar a nova atividade
            context.startActivity(intent)
        }

        return view
    }

}







