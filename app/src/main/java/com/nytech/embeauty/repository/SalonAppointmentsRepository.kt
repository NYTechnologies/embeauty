package com.nytech.embeauty.repository

import android.app.Activity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.view.salon.NewAppointmentActivity

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

    // Create: Função para cadastrar um novo Agendamento (Appointment)
    fun registerNewSalonAppointment(
        activity: Activity,
        appointment: SalonAppointments.Appointment
    ) {
        // Pega o dia no formato [dd/MM/yyyy] que está na propriedade startDateTime [dd/MM/yyyy, HH:mm]
        val date = appointment.startDateTime.split(",")[0]
        // Passa o parâmetro 'date' para a função de pegar a referência do documento
        val salonAppointmentsDocumentReference = getSalonAppointmentsDocumentReferenceByDate(date)

        salonAppointmentsDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonAppointments
                val salonAppointments = documentSnapshot.toObject(SalonAppointments::class.java)

                if (salonAppointments != null) {
                    // Faz uma cópia dos agendamentos existentes para poder atualizar e adiciona o novo agendamento a essa lista
                    val updatedAppointments = salonAppointments.appointments.toMutableList()
                    updatedAppointments.add(appointment)

                    // Substitui a lista antiga pela lista com o novo agendamento adicionado
                    salonAppointments.appointments = updatedAppointments

                    // Com a nova Lista de agendamentos atualizada, faz uma nova chamada ao Database para atualizar lá tbm
                    salonAppointmentsDocumentReference
                        .set(salonAppointments)
                        .addOnSuccessListener {
                            // Caso tenha sucesso na atualização do agendamento, retornar para a SalonMainActivity chamando a função registerNewAppointmentSuccess() da NewAppointmentActivity
                            when (activity) {
                                is NewAppointmentActivity -> activity.registerNewAppointmentSuccess()
                            }
                        }.addOnFailureListener {
                            TODO("Implementar alguma mensagem quando der erro no cadastro")
                        }
                } else {
                    registerFirstSalonAppointments(activity, appointment)
                }
            }
    }


    // Create: Função para cadastrar Agendamentos assim que a conta do Salão for criada
    fun registerFirstSalonAppointments(
        activity: Activity,
        appointment: SalonAppointments.Appointment
    ) {
        // Pega o dia no formato [dd/MM/yyyy] que está na propriedade startDateTime [dd/MM/yyyy, HH:mm]
        val date = appointment.startDateTime.split(",")[0]
        val salonAppointmentsDocumentReference = getSalonAppointmentsDocumentReferenceByDate(date)

        val firstAppointment = listOf(appointment)
        val salonFirstAppointments = SalonAppointments()
        salonFirstAppointments.appointments = firstAppointment

        salonAppointmentsDocumentReference
            .set(salonFirstAppointments)
            .addOnSuccessListener {
                // Caso tenha sucesso na atualização do agendamento, retornar para a SalonMainActivity chamando a função registerNewAppointmentSuccess() da NewAppointmentActivity
                when (activity) {
                    is NewAppointmentActivity -> activity.registerNewAppointmentSuccess()
                }
            }.addOnFailureListener {
                TODO("Implementar alguma mensagem quando der erro no cadastro")
            }
    }

    // Update: função para editar um agendamento existente utilizando o UUID para encontrá-lo no firestore
    fun updateSalonAppointment(
        activity: Activity,
        appointmentNewInfo: SalonAppointments.Appointment,
        oldStartDateTime: String,
        onComplete: () -> Unit
    ) {
        // Pega o dia no formato [dd/MM/yyyy] que está na propriedade startDateTime [dd/MM/yyyy, HH:mm]
        val oldDate = oldStartDateTime.split(",")[0]
        val salonAppointmentsDocumentReference =
            getSalonAppointmentsDocumentReferenceByDate(oldDate)

        salonAppointmentsDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonAppointments
                val salonAppointments = documentSnapshot.toObject(SalonAppointments::class.java)

                if (salonAppointments != null) {
                    // Faz uma cópia dos agendamentos existentes para poder atualizar um agendamento
                    val updatedAppointments = salonAppointments.appointments.toMutableList()

                    // Encontra o agendamento a ser atualizado pelo UUID
                    val appointmentToBeUpdated =
                        updatedAppointments.find { it.uuid == appointmentNewInfo.uuid }
                    if (appointmentToBeUpdated != null) {
                        // Atualiza as informações do agendamento com as novas informações de agendamento
                        val updatedAppointment = appointmentNewInfo
                        val indexOfAppointmentToBeUpdated =
                            updatedAppointments.indexOf(appointmentToBeUpdated)
                        updatedAppointments[indexOfAppointmentToBeUpdated] = updatedAppointment

                        salonAppointments.appointments = updatedAppointments

                        // Com a nova lista de agendamentos atualizadas, faz uma nova chamada ao Database para colocar essa nova lista no lugar da anterior
                        salonAppointmentsDocumentReference
                            .set(salonAppointments)
                            .addOnSuccessListener {
                                // Caso tenha sucesso na atualização do serviço
                                onComplete()
                            }
                            .addOnFailureListener {
                                // Em caso de falha na atualização do serviço
                                TODO("Implementar alguma mensagem quando ocorrer erro na atualização")
                            }
                    }
                }
            }
    }

    // Delete: Função para excluir um agendamento usando UUID
    fun deleteSalonAppointment(appointment: SalonAppointments.Appointment, onComplete: () -> Unit) {
        // Pega o dia no formato [dd/MM/yyyy] que está na propriedade startDateTime [dd/MM/yyyy, HH:mm] do appointment
        val startDate = appointment.startDateTime.split(",")[0]
        val salonAppointmentsDocumentReference =
            getSalonAppointmentsDocumentReferenceByDate(startDate)

        salonAppointmentsDocumentReference
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // Caso a requisição do get() der sucesso, devolve os dados de SalonAppointments
                val salonAppointments = documentSnapshot.toObject(SalonAppointments::class.java)
                if (salonAppointments != null) {
                    // Faz uma cópia dos agendamentos existentes para poder excluir um agendamento
                    val updatedAppointments = salonAppointments.appointments.toMutableList()

                    // Encontra o agendamento a ser excluído pelo UUID
                    val appointmentToBeDeleted =
                        updatedAppointments.find { it.uuid == appointment.uuid }
                    if (appointmentToBeDeleted != null) {
                        // Remove o agendamento com aquele UUID que encontramos acima
                        updatedAppointments.remove(appointmentToBeDeleted)
                        // Substitui a lista antiga pela lista com o agendamento deletado
                        salonAppointments.appointments = updatedAppointments

                        // Com a nova lista de agendamentos atualizadas, faz uma nova chamada ao Database para colocar essa nova lista no lugar da anterior
                        salonAppointmentsDocumentReference
                            .set(salonAppointments)
                            .addOnSuccessListener {
                                // Caso tenha sucesso na atualização do serviço
                                onComplete()
                            }
                            .addOnFailureListener {
                                // Em caso de falha na atualização do serviço
                                TODO("Implementar alguma mensagem quando ocorrer erro na atualização")
                            }
                    }
                }
            }
    }
}