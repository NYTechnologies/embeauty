package com.nytech.embeauty.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.nytech.embeauty.R
import com.nytech.embeauty.adapter.SalonServicesAdapter
import com.nytech.embeauty.repository.SalonServicesRepository
import com.nytech.embeauty.view.salon.NewServiceActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SalonServicesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SalonServicesFragment : Fragment(), ServiceDeletedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val salonServicesRepository: SalonServicesRepository = SalonServicesRepository()

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
        return inflater.inflate(R.layout.fragment_salon_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listViewSalonServices)

        listView.divider = null

        salonServicesRepository.getSalonServices { services ->
            Log.d("onViewCreated", "Services: $services")
            // Aqui temos acesso aos serviços do salão. Chamar classe de adapter (SalonServicesAdapter) e lançar esses dados numa ListView
            listView.adapter = SalonServicesAdapter(requireActivity(), requireContext(), services.services, this@SalonServicesFragment)
        }

        // botão para ir para a activity de adicionar um serviço
        view.findViewById<Button>(R.id.buttonNewService).setOnClickListener {
            // Intent declarando de onde vc está saindo e para onde quer ir
            val intent = Intent(activity, NewServiceActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        salonServicesRepository.getSalonServices { services ->
            Log.d("onResume", "Services: $services")
            // Aqui temos acesso aos serviços do salão. Chamar classe de adapter (SalonServicesAdapter) e lançar esses dados numa ListView
            listView.adapter = SalonServicesAdapter(requireActivity(), requireContext(), services.services, this@SalonServicesFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SalonServicesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SalonServicesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onServiceDeleted() {
        salonServicesRepository.getSalonServices { services ->
            Log.d("onServiceDeleted", "Services: $services")
            listView.adapter = SalonServicesAdapter(requireActivity(), requireContext(), services.services, this@SalonServicesFragment)
        }
    }
}

interface ServiceDeletedListener {
    fun onServiceDeleted()
}