package com.nytech.embeauty.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.nytech.embeauty.R
import com.nytech.embeauty.model.SalonAppointments

class SalonAppointmentsAdapter(
    private val context: Context,
    private val salonAppointments: List<SalonAppointments.Appointment>
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

        val formattedStartTime = salonAppointments[position].startDateTime.split(",")[1].trim()
        val formattedEndTime = salonAppointments[position].endDateTime.split(",")[1].trim()

        // Coloca as informações vinda do Backend nos devidos lugares
        serviceName.text = salonAppointments[position].serviceName
        clientName.text = salonAppointments[position].clientName
        startAt.text = formattedStartTime
        endAt.text = formattedEndTime

        // Lógica para verificar se o agendamento já expirou o horário baseado no startTimestamp e endTimestamp
        val startTimestamp = salonAppointments[position].startTimestamp!!.seconds
        val endTimestamp = salonAppointments[position].endTimestamp!!.seconds

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

        return view
    }
}