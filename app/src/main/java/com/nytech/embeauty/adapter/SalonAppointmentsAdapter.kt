package com.nytech.embeauty.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.nytech.embeauty.R
import com.nytech.embeauty.fragment.AppointmentDeletedListener
import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.repository.SalonAppointmentsRepository
import com.nytech.embeauty.repository.SalonServicesRepository
import com.nytech.embeauty.view.salon.UpdateAppointmentActivity

class SalonAppointmentsAdapter(
    private val context: Context,
    private val salonAppointments: List<SalonAppointments.Appointment>,
    private val appointmentDeletedListener: AppointmentDeletedListener
) : ArrayAdapter<SalonAppointments.Appointment>(context, R.layout.appointments_list_item, salonAppointments) {


    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.appointments_list_item, null)

        // Captura as propriedades de cada item da lista
        val serviceName: TextView = view.findViewById(R.id.salonAppointmentServiceName)
        val clientName: TextView = view.findViewById(R.id.salonAppointmentClientName)
        val startAt: TextView = view.findViewById(R.id.salonAppointmentStartAt)
        val endAt: TextView = view.findViewById(R.id.salonAppointmentEndAt)
        val barVertical: View = view.findViewById(R.id.salonAppointmentBarVertical)

        val appointment = salonAppointments[position]

        val formattedStartTime = appointment.startDateTime.split(",")[1].trim()
        val formattedEndTime = appointment.endDateTime.split(",")[1].trim()
        val appointmentUUID = appointment.uuid

        // Coloca as informações vinda do Backend nos devidos lugares
        serviceName.text = appointment.serviceName
        clientName.text = appointment.clientName
        startAt.text = formattedStartTime
        endAt.text = formattedEndTime

        // Lógica para verificar se o agendamento já expirou o horário baseado no startTimestamp e endTimestamp
        val startTimestamp = appointment.startTimestamp!!.seconds
        val endTimestamp = appointment.endTimestamp!!.seconds

        val currentTimestamp = Timestamp.now().seconds

        when {
            currentTimestamp > endTimestamp  -> {
                // O agendamento já passou do horário de término (end)
                barVertical.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_dark))
                serviceName.paintFlags = serviceName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                clientName.paintFlags = clientName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                startAt.paintFlags = startAt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                endAt.paintFlags = endAt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
            else -> {
                // O agendamento ainda não chegou no horário de início (start)
                barVertical.setBackgroundColor(ContextCompat.getColor(context, R.color.calendar_green))
            }
        }

        // Lógica do botão de editar o agendamento
        val editAppointmentButton = view.findViewById<ImageButton>(R.id.salonAppointmentEditButton)
        editAppointmentButton.setOnClickListener {

            // Criar a intenção (Intent) para a activity de edição de serviço
            val intent = Intent(context, UpdateAppointmentActivity::class.java)

            intent.putExtra("uuid", appointmentUUID)
            intent.putExtra("old_client_name", salonAppointments[position].clientName)
            intent.putExtra("old_client_phone", salonAppointments[position].clientPhone)
            intent.putExtra("old_start_date_time", salonAppointments[position].startDateTime)
            intent.putExtra("old_duration", salonAppointments[position].durationMinutes.toString())
            intent.putExtra("old_service_name", salonAppointments[position].serviceName)

            context.startActivity(intent)

        }

        // Lógica do botão de deletar o agendamento
        val deleteAppointmentButton = view.findViewById<ImageButton>(R.id.salonAppointmentDeleteButton)
        deleteAppointmentButton.setOnClickListener {
            showDeleteConfirmationOfAppointment(appointment)
        }

        return view
    }

    private fun showDeleteConfirmationOfAppointment(appointment: SalonAppointments.Appointment) {
        // Lógica do Alert Dialog de confirmação da exclusão do agendamento
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Confirmar exclusão")
            .setMessage("Tem certeza que deseja excluir o agendamento do cliente ${appointment.clientName}, agendado para ${appointment.startDateTime}?")
            .setPositiveButton("Sim") { _, _ ->
                val salonAppointmentsRepository = SalonAppointmentsRepository()
                salonAppointmentsRepository.deleteSalonAppointment(appointment) {
                    // Lógica a ser executada quando a exclusão for concluída com sucesso
                    Toast.makeText(context, "Agendamento excluído com sucesso", Toast.LENGTH_SHORT).show()
                    appointmentDeletedListener.onAppointmentDeleted()
                }
            }
            .setNegativeButton("Não", null)
            .create()
            .show()
    }
}
