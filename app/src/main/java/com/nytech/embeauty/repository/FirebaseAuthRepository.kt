package com.nytech.embeauty.repository

import com.google.firebase.auth.FirebaseAuth

/**
 * Repositório para gerenciamento dos processos de autenticação de usuário
 * via FirebaseAuth
 */
class FirebaseAuthRepository {

    // Instancia uma conexão com o FirebaseAuth
    fun myFirebaseAuth() = FirebaseAuth.getInstance()

    // Captura o User ID do usuário do app que está logado na nossa aplicação
    fun getCurrentUserID(): String = myFirebaseAuth().currentUser!!.uid

}