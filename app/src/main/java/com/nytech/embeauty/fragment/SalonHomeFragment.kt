package com.nytech.embeauty.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.nytech.embeauty.R
import com.nytech.embeauty.adapter.SalonHomeAdapter
import com.nytech.embeauty.repository.SalonAppointmentsRepository
import com.nytech.embeauty.repository.SalonProfileRepository
import com.nytech.embeauty.utils.getCurrentDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SalonHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SalonHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Instancia as classes de Repository do Profile e do Agendamento para usar na Home
    private val salonProfileRepository: SalonProfileRepository = SalonProfileRepository()
    private val salonAppointmentsRepository: SalonAppointmentsRepository = SalonAppointmentsRepository()

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_salon_home, container, false)

        listView = view.findViewById(R.id.listViewSalonHome)

        // Data no formato usado na Home
        val homeDate = getHomeDateFormat()

        val dataText = view.findViewById<TextView>(R.id.diaDeHojeTextView)
        dataText.text = homeDate

        // Busca via SalonProfileRepository os dados de Profile do Salão, para usar o Nome do Salão na frase de 'bem-vindo(a)'
        salonProfileRepository.getSalonProfile { salonProfile ->
            Log.d("SalonHomeFragment", "Cliente: $salonProfile")
            val welcomeText = view.findViewById<TextView>(R.id.welcomeTextView)
            welcomeText.text = "bem-vindo (a), ${salonProfile.name},"
        }

        // Busca via SalonAppointmentsRepository os agendamentos de HOJE do Salão
        salonAppointmentsRepository.getSalonAppointmentsByDate(getCurrentDate()) { salonAppointments ->
            Log.d("SalonHomeFragment", "Agendamentos de hoje: ${salonAppointments.appointments}")
            // ordena os agendamentos com base no startTimestamp
            val sortedAppointments = salonAppointments.appointments.sortedBy { it.startTimestamp }

                // Passamos para o adapter (SalonHomeAdapter) os agendamentos de hoje para ele serializar e disponibilizar a Interface (Tela)
            listView.adapter = SalonHomeAdapter(requireContext(), sortedAppointments)
        }

        return view
    }

    // Função para resgatar a data do dia o formato necessário da Home
    private fun getHomeDateFormat(): String {
        // Formata a data de hoje em '<dia> de <mês>, <dia da semana>'
        val todayDate = LocalDateTime.now()
        // Formato desejado da data
        val formatDate = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("pt", "BR"))
        // String com a data formatada
        return todayDate.format(formatDate)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SalonHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SalonHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}