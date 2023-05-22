package com.nytech.embeauty.repository

import android.app.Activity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nytech.embeauty.model.SalonProfile
import com.nytech.embeauty.view.salon.SalonRegisterActivity


/**
 * Repositório para gerenciamento das informações de Profile dos Salões
 */
class SalonProfileRepository {

    companion object Constants {
        const val SALON_PROFILE_COLLECTION = "SalonProfile"
    }

    // Instancia uma conexão como o banco de dados FireStore
    private val myFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Instancia nossa classe de gerenciamento do FirebaseAuth (autenticação do usuário do App)
    private val firebaseAuthRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    // Captura a referência do documento SalonProfile para o cliente logado no FireStore
    private fun getSalonProfileDocumentReference(): DocumentReference =
        myFirestore
            .collection(SALON_PROFILE_COLLECTION)
            .document(firebaseAuthRepository.getCurrentUserID())

    // Função para buscar os dados de Profile do Salão
    fun getSalonProfile(onComplete: (SalonProfile) -> Unit) {
        val salonProfileDocumentReference = getSalonProfileDocumentReference()

        salonProfileDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de Profile
                val salonProfile = documentSnapshot.toObject(SalonProfile::class.java)
                onComplete(salonProfile ?: SalonProfile())
            }.addOnFailureListener {
                // Se a requisição do get() der alguma falha, devolve um Profile Vazio
                onComplete(SalonProfile())
            }
    }

    // Função para cadastrar um novo Salão
    fun registerNewSalonProfile(activity: Activity, salonProfile: SalonProfile) {
        val salonProfileDocumentReference = getSalonProfileDocumentReference()

        salonProfileDocumentReference
            .set(salonProfile)
            .addOnCompleteListener {
                // Caso a req de set() tenha sucesso no cadastro do Salão, direcionar para a SalonMainActivity
                when (activity) {
                    is SalonRegisterActivity -> {
                        activity.salonRegisterSuccess()
                    }
                }
            }.addOnFailureListener {
                // Caso a req de set() falhe no cadastro do Salão
                TODO("Implementar alguma mensagem quando der erro no cadastro")
            }
    }
}