package com.nytech.embeauty.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.nytech.embeauty.R
import com.nytech.embeauty.model.SalonAppointments
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SalonHomeAdapter(
    private val context: Context,
    private val salonAppointments: List<SalonAppointments.Appointment>
) : ArrayAdapter<SalonAppointments.Appointment>(context, R.layout.home_list_appointments, salonAppointments) {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.home_list_appointments, null)

        // Captura as propriedades de cada item da lista
        val serviceName: TextView = view.findViewById(R.id.salonHomeServiceName)
        val clientName: TextView = view.findViewById(R.id.salonHomeClientName)
        val startAt: TextView = view.findViewById(R.id.salonHomeAppointmentStartAt)
        val endAt: TextView = view.findViewById(R.id.salonHomeAppointmentEndAt)
        val barVertical: View = view.findViewById(R.id.salonHomeBarVertical)

        val formattedStartTime = salonAppointments[position].startDateTime.split(",")[1].trim()
        val formattedEndTime = salonAppointments[position].endDateTime.split(",")[1].trim()

        // Coloca as informações vinda do Backend nos devidos lugares
        serviceName.text = salonAppointments[position].serviceName
        clientName.text = salonAppointments[position].clientName
        startAt.text = formattedStartTime
        endAt.text = formattedEndTime

/*        // Lógica para verificar se o agendamento já expirou o horário (endAt)
        val currentTime = Calendar.getInstance()
        val startAtTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(formattedStartTime)
        val startAtCalendar = Calendar.getInstance().apply { time = startAtTime!! }
        val endAtTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(formattedEndTime)
        val endAtCalendar = Calendar.getInstance().apply { time = endAtTime!! }

        when {
            currentTime.time >= endAtCalendar.time -> {
                // Horário já passou, definir cor cinza
                barVertical.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_dark))
            }
            currentTime.time >= startAtCalendar.time && currentTime.time < endAtCalendar.time -> {
                // Horário está entre startAt e endAt, definir cor amarela
                barVertical.setBackgroundColor(ContextCompat.getColor(context, R.color.safety_yellow))
            }
            else -> {
                // Horário ainda não começou, definir cor verde
                barVertical.setBackgroundColor(ContextCompat.getColor(context, R.color.green_primary))
            }
        }*/

        return view
    }
}