package com.nytech.embeauty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nytech.embeauty.R
import com.nytech.embeauty.model.SalonModel

class ServicesAdapter(context: Context, services: MutableList<SalonModel.Service>) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    private val servicesList = services

    override fun getCount(): Int {
        return servicesList.size
    }

    override fun getItem(position: Int): Any {
        return servicesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            //view = inflater.inflate(R.layout.service_list_item, parent, false)
        }

        val service = servicesList[position]

//        view?.findViewById<TextView>(R.id.service_name)?.text = service.name
//        view?.findViewById<TextView>(R.id.service_duration)?.text = service.duration
//        view?.findViewById<TextView>(R.id.service_price)?.text = service.price

        return view!!
    }

    fun updateServices(services: List<SalonModel.Service>) {
        servicesList.clear()
        servicesList.addAll(services)
    }
}

//class MyFragment : Fragment() {
//
//    private val salonRepository = SalonRepository()
//    private lateinit var servicesAdapter: ServicesAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        servicesAdapter = ServicesAdapter(requireContext(), mutableListOf())
//
//        val salonId = "your_salons_firebase_uid"
//
//        salonRepository.getServicesForSalon(salonId) { services ->
//            servicesAdapter.updateServices(services)
//            servicesAdapter.notifyDataSetChanged()
//        }
//
//        listView.adapter = servicesAdapter
//    }
//No exemplo acima, criamos uma instância do SalonRepository e um ServicesAdapter que estende a classe BaseAdapter. No método onViewCreated, chamamos o método getServicesForSalon do SalonRepository, passando o firebaseUID do salão desejado e um callback que atualiza a lista de serviços no adapter e notifica o adapter sobre as mudanças. Finalmente, definimos o adapter do ListView como o ServicesAdapter.
//
//O ServicesAdapter é responsável por inflar a view de cada item do ListView e preencher as informações do serviço em cada item. No método updateServices, limpamos a lista atual de serviços e adicionamos a nova lista recebida como parâmetro. No método getView, obtemos a view do item do ListView e preenchemos as informações do serviço com os dados da lista de serviços.
//
//Certifique-se de substituir R.layout.service_list_item pelo layout do item do ListView que você deseja usar.







