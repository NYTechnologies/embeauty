package com.nytech.embeauty.repository

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nytech.embeauty.model.SalonModel
import com.nytech.embeauty.view.salon.SalonRegisterActivity


/**
 * Repositório para gerenciamento dos dados dos Salões
 * Nesse repositório há funções de gerenciamento de Salão, Serviços do Salão e Agendamentos do Salão
 */
class SalonRepository {

    companion object Constants {
        const val SALON_COLLECTION = "Salon"
        const val SERVICES_COLLECTION = "services"
    }

    // Captura referência do banco de dados FireStore
    private val myFirestore = FirebaseFirestore.getInstance()

    // Captura o User ID do usuário do app que está logado na nossa aplicação
    fun getCurrentUserID(): String = FirebaseAuth.getInstance().currentUser!!.uid

    // Função para cadastrar novo Salão
    fun registerSalon(activity: Activity, salonModel: SalonModel) {
        myFirestore
            .collection(SALON_COLLECTION)
            .document(getCurrentUserID())
            .set(salonModel)
            .addOnCompleteListener {
                // Caso tenha sucesso no cadastro do salão, direcionar para a SalonMainActivity
                when(activity) {
                    is SalonRegisterActivity -> {
                        activity.salonRegisterSuccess()
                    }
                }
            }
            .addOnFailureListener {
                TODO("Implementar alguma mensagem quando der erro no cadastro")
            }
    }

    // função para retornar os serviços do salão que estiver logado no app
    fun getServices(onResult: (List<SalonModel.Service>) -> Unit) {
        myFirestore
            .collection(SALON_COLLECTION)
            .document(getCurrentUserID())
            .collection(SERVICES_COLLECTION)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // se der certo
                val servicesList = mutableListOf<SalonModel.Service>()
                querySnapshot.documents.forEach { document ->
                    val data = document.data
                    servicesList.add(SalonModel.Service(
                        data!!["name"] as String,
                        data["price"] as String,
                        data["duration"] as String
                    ))
                }
                Log.d("UserRepository", "Services returned: $servicesList")
                onResult(servicesList)
            }
            .addOnFailureListener { exception ->
                // se der errado
                Log.w("UserRepository", "Error getting services: ", exception)
            }
    }


}