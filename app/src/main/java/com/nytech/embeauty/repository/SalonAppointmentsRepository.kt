package com.nytech.embeauty.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nytech.embeauty.model.SalonAppointments

/**
 * Repositório para gerenciamento das informações de Agendamentos (Appointments) dos Salões
 */
class SalonAppointmentsRepository {

    companion object Constants {
        const val SALON_APPOINTMENTS_COLLECTION = "SalonAppointments"
    }

    // Instancia uma conexão como o banco de dados FireStore
    private val myFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Instancia nossa classe de gerenciamento do FirebaseAuth (autenticação do usuário do App)
    private val firebaseAuthRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    // Captura a referência do documento SalonAppointments para o cliente logado no FireStore
    private fun getSalonAppointmentsDocumentReferenceByDate(date: String): DocumentReference =
        myFirestore
            .collection(SALON_APPOINTMENTS_COLLECTION)
            .document(firebaseAuthRepository.getCurrentUserID() + "#" + date)

    /* ---------------------------------------------------------------- */
    /*                Métodos do CRUD para o SalonAppointments          */
    /* ---------------------------------------------------------------- */

    // Read: Função para buscar todos os Agendamentos do Salão
    fun getSalonAppointmentsByDate(date: String, onComplete: (SalonAppointments) -> Unit) {
        // Passa o parâmetro 'date' para a função de pegar a referência do documento
        val salonAppointmentsDocumentReference = getSalonAppointmentsDocumentReferenceByDate(date)

        salonAppointmentsDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonAppointments
                val salonAppointments = documentSnapshot.toObject(SalonAppointments::class.java)
                onComplete(salonAppointments ?: SalonAppointments())
            }.addOnFailureListener {
                // Caso a requisição do get() falhar, devolve um SalonAppointments vazio
                onComplete(SalonAppointments())
            }
    }
}