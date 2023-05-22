package com.nytech.embeauty.repository

import android.app.Activity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nytech.embeauty.model.SalonServices
import com.nytech.embeauty.view.salon.NewServiceActivity

class SalonServicesRepository {

    companion object Constants {
        const val SALON_SERVICES_COLLECTION = "SalonServices"
    }

    // Instancia uma conexão como o banco de dados FireStore
    private val myFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Instancia nossa classe de gerenciamento do FirebaseAuth (autenticação do usuário do App)
    private val firebaseAuthRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    // Captura a referência do documento SalonServices para o cliente logado no FireStore
    private fun getSalonServicesDocumentReference(): DocumentReference =
        myFirestore
            .collection(SALON_SERVICES_COLLECTION)
            .document(firebaseAuthRepository.getCurrentUserID())

    /* ---------------------------------------------------------------- */
    /*                Métodos do CRUD para o SalonServices              */
    /* ---------------------------------------------------------------- */

    // Create: Função para registrar um novo serviço
    fun registerNewSalonService(activity: Activity, service: SalonServices.Service) {
        val salonServicesDocumentReference = getSalonServicesDocumentReference()

        salonServicesDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonServices
                val salonServices = documentSnapshot.toObject(SalonServices::class.java)

                if (salonServices != null) {
                    // Faz uma cópia dos serviços existentes para poder atualizar e adiciona o novo serviço a essa lista
                    val updatedServices = salonServices.services.toMutableList()
                    updatedServices.add(service)
                    // Substitui a lista antiga pela lista com o novo serviço adicionado
                    salonServices.services = updatedServices

                    // Com a nova Lista de Serviços atualizada, faz uma nova chamada ao Database para atualizar lá tbm
                    salonServicesDocumentReference
                        .set(salonServices)
                        .addOnSuccessListener {
                            // Caso tenha sucesso na atualização do serviço, retornar para a SalonMainActivity chamando a função registerNewServiceSuccess() da NewServiceActivity
                            when (activity) {
                                is NewServiceActivity -> activity.registerNewServiceSuccess()
                            }
                        }.addOnFailureListener {
                            TODO("Implementar alguma mensagem quando der erro no cadastro")
                        }
                }
            }
    }

    // Create: Função para cadastrar serviços assim que a conta do Salão for criada
    fun registerFirstSalonServices(salonServices: SalonServices) {
        val salonServicesDocumentReference = getSalonServicesDocumentReference()

        salonServicesDocumentReference
            .set(salonServices)
    }

    // Read: Função para buscar todos os Serviços do salão
    fun getSalonServices(onComplete: (SalonServices) -> Unit) {
        val salonServicesDocumentReference = getSalonServicesDocumentReference()

        salonServicesDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonServices
                val salonServices = documentSnapshot.toObject(SalonServices::class.java)
                onComplete(salonServices ?: SalonServices())
            }.addOnFailureListener {
                // Caso a requisição do get() falhar, devolve um SalonServices vazio
                onComplete(SalonServices())
            }
    }

    // Update: Função para atualiza um serviço existente
    fun updateSalonServiceByOldName(oldSalonServiceName: String, newName: String, newPrice: String, onComplete: () -> Unit) {
        val salonServicesDocumentReference = getSalonServicesDocumentReference()

        salonServicesDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonServices
                val salonServices = documentSnapshot.toObject(SalonServices::class.java)

                if (salonServices != null) {
                    // Faz uma cópia dos serviços existentes para poder atualizar
                    val updatedServices = salonServices.services.toMutableList()

                    // Encontra o Serviço a ser atualizado usando o nome antigo (oldSalonServiceName)
                    val serviceToBeUpdated = updatedServices.find { it.name == oldSalonServiceName }
                    if (serviceToBeUpdated != null) {
                        // Atualiza as informações do Serviço com o newName e newPrice
                        val updatedService = serviceToBeUpdated.copy(name = newName, price = newPrice)
                        val indexOfServiceToBeUpdated = updatedServices.indexOf(serviceToBeUpdated)
                        updatedServices[indexOfServiceToBeUpdated] = updatedService

                        salonServices.services = updatedServices

                        // Com a nova Lista de Serviços atualizada, faz uma nova chamada ao Database para atualizar lá tbm
                        salonServicesDocumentReference
                            .set(salonServices)
                            .addOnSuccessListener {
                                // Caso tenha sucesso na atualização do serviço
                                onComplete()
                            }.addOnFailureListener {
                                // Em caso de falha na atualização do serviço
                                TODO("Implementar alguma mensagem quando ocorrer erro na atualização")
                            }
                    }
                }
            }
    }

    // Delete: Função para excluir um serviço usando nome
    fun deleteSalonService(serviceName: String, onComplete: () -> Unit) {
        val salonServicesDocumentReference = getSalonServicesDocumentReference()

        salonServicesDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonServices
                val salonServices = documentSnapshot.toObject(SalonServices::class.java)

                if (salonServices != null) {
                    // Faz uma cópia dos serviços existentes para poder remover o serviço dessa lista
                    val updatedServices = salonServices.services.toMutableList()

                    // Encontra o serviço a ser excluído pelo nome
                    val serviceToBeDeleted = updatedServices.find { it.name == serviceName }
                    if (serviceToBeDeleted != null) {
                        updatedServices.remove(serviceToBeDeleted)
                        // Substitui a lista antiga pela lista com o serviço deletado
                        salonServices.services = updatedServices

                        // Com a nova Lista de Serviços atualizada, faz uma nova chamada ao Database para atualizar lá tbm
                        salonServicesDocumentReference
                            .set(salonServices)
                            .addOnSuccessListener {
                                // Caso tenha sucesso na exclusão do serviço
                                onComplete()
                            }.addOnFailureListener {
                                // Em caso de falha na exclusão do serviço
                                TODO("Implementar alguma mensagem quando ocorrer erro na exclusão")
                            }
                    }
                }
            }
    }

}