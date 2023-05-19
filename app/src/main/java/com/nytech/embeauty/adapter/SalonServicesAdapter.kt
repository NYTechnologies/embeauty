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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.nytech.embeauty.R
import com.nytech.embeauty.UpdateServiceActivity
import com.nytech.embeauty.constants.IntentConstants
import com.nytech.embeauty.model.SalonModel
import com.nytech.embeauty.repository.SalonRepository
import com.nytech.embeauty.view.salon.SalonMainActivity

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

        // Lógica do botão de deletar o serviço
        val deleteServiceButton = view.findViewById<ImageButton>(R.id.salonServiceDeleteButton)
        deleteServiceButton.setOnClickListener {
            val serviceNameToDelete = serviceName.text.toString()
            showDeleteConfirmationDialog(serviceNameToDelete)
        }

        return view
    }

    private fun showDeleteConfirmationDialog(serviceName: String) {

        // Lógica do Alert Dialog de confirmação da exclusão do serviço
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Confirmar exclusão")
            .setMessage("Tem certeza de que deseja excluir o serviço '$serviceName'?")
            .setPositiveButton("Sim") { _, _ ->
                val salonRepository = SalonRepository()
                salonRepository.deleteService(serviceName) {
                    // Lógica a ser executada quando a exclusão for concluída com sucesso
                    Toast.makeText(context, "Serviço excluído com sucesso", Toast.LENGTH_SHORT).show()

                    // se o cadastro do novo serviço for completado com sucesso, voltar a SalonMainActivity
                    val intent = Intent(context, SalonMainActivity::class.java)

                    // Envia para o SalonMainActivity dizendo para ir para o Fragment de Serviços
                    intent.putExtra(IntentConstants.TARGET_FRAGMENT, IntentConstants.SALON_SERVICES_FRAGMENT)
                    context.startActivity(intent)
                }
            }
            .setNegativeButton("Não", null)
            .create()
            .show()
    }

}







