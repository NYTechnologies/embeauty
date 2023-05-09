package com.nytech.embeauty.repository

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import com.nytech.embeauty.model.SalonModel
import com.nytech.embeauty.model.getCurrentDate
import com.nytech.embeauty.view.salon.SalonRegisterActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
                when (activity) {
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
    fun getServicesForSalon(onComplete: (List<SalonModel.Service>) -> Unit) {
        myFirestore
            .collection(SALON_COLLECTION)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // se der sucesso devolve uma lista com os serviços
                val salonModel = documentSnapshot.toObject(SalonModel::class.java)
                onComplete(salonModel?.services ?: emptyList())
            }.addOnFailureListener {
                // se falhar devolve uma lista vazia
                onComplete(emptyList())
            }
    }

    // função para retornar os agendamentos do salão que estiver logado no app
    fun getAppointmentsOfTheDayForSalon(onComplete: (List<SalonModel.Appointment>) -> Unit) {
        myFirestore
            .collection(SALON_COLLECTION)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // se der sucesso devolve uma lista com os serviços
                val salonModel = documentSnapshot.toObject(SalonModel::class.java)
                onComplete(salonModel?.appointments?.filter { it.date == getCurrentDate() } ?: emptyList())
            }.addOnFailureListener {
                // se falhar devolve uma lista vazia
                onComplete(emptyList())
            }
    }
}
