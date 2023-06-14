package com.nytech.embeauty.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import com.nytech.embeauty.R
import com.nytech.embeauty.adapter.SalonAppointmentsAdapter
import com.nytech.embeauty.constants.GenericConstants
import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.repository.SalonAppointmentsRepository
import com.nytech.embeauty.view.salon.NewAppointmentActivity
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SalonAppointmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SalonAppointmentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val salonAppointmentsRepository: SalonAppointmentsRepository =
        SalonAppointmentsRepository()
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_salon_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listSalonAppointments)

        // Botão de novo agendamento
        view.findViewById<Button>(R.id.buttonAppointment).setOnClickListener {
            // Intent indo para a NewAppointmentActivity
            val intent = Intent(activity, NewAppointmentActivity::class.java)
            startActivity(intent)
        }

        // Verifica o click no calendário
        view.findViewById<CalendarView>(R.id.appointmentsCalendar)
            .setOnDateChangeListener { _, year, month, day ->
                // Pega a data selecionada e coloca no formato dd/MM/yyyy
                val selectedDate =
                    String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year)

                salonAppointmentsRepository.getSalonAppointmentsByDate(selectedDate) { salonAppointments ->
                    Log.d(
                        "SalonAppointmentsFragment",
                        "Agendamentos de $selectedDate: ${salonAppointments.appointments}"
                    )
                    // ordena os agendamentos com base no startTimestamp
                    val sortedAppointments =
                        salonAppointments.appointments.sortedBy { it.startTimestamp }
                    // Passamos para o adapter (SalonHomeAdapter) os agendamentos de hoje para ele serializar e disponibilizar a Interface (Tela)
                    listView.adapter =
                        SalonAppointmentsAdapter(requireContext(), sortedAppointments)
                }


            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SalonAppointmentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SalonAppointmentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}