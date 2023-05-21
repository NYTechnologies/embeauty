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
import com.nytech.embeauty.databinding.FragmentSalonHomeBinding
import com.nytech.embeauty.repository.SalonRepository
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

    private val salonRepository: SalonRepository = SalonRepository()
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

        // Formata a data de hoje em '<dia> de <mês>, <dia da semana>'
        val dataAtual = LocalDateTime.now()
        // Formato desejado da data
        val formatoData = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("pt", "BR"))
        // String com a data formatada
        val dataFormatada = dataAtual.format(formatoData)

        //resgata a data do dia
        val dataText = view.findViewById<TextView>(R.id.diaDeHojeTextView)
        dataText.text = dataFormatada

        //resgata o nome do usuário e seus agendamentos do dia
        salonRepository.getSalon { salon ->
            Log.d("SalonHomeFragment", "Cliente: $salon")
            val welcomeText = view.findViewById<TextView>(R.id.welcomeTextView)
            welcomeText.text = "bem-vindo (a), ${salon.name},"

            Log.d("SalonHomeFragment", "Agendamentos de hoje: ${salon.todayAppointments()}")
            // Passamos para o adapter os agendamentos de hoje
            listView.adapter = SalonHomeAdapter(requireContext(), salon.todayAppointments())
        }
        return view
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