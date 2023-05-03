package com.nytech.embeauty.view.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nytech.embeauty.constants.FirebaseConstants.FIRESTORE_SALON_DB
import com.nytech.embeauty.constants.ToastTextConstants.CADASTRO_REALIZADO_COM_SUCESSO
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_O_NOME_FANTASIA
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_O_NUMERO_TELEFONE
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SEU_EMAIL
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SUA_SENHA
import com.nytech.embeauty.databinding.ActivitySalonRegisterBinding
import com.nytech.embeauty.model.SalonModel

/**
 * Tela de Cadastro para salão
 */
class SalonRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonRegisterBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()

        supportActionBar?.hide()

        setContentView(binding.root)

        // botão para a tela de login do salão
        binding.textLoginSalon.setOnClickListener {
            onBackPressed()
        }

        // lógica de cadastro do novo salão no Firebase
        binding.buttonRegisterSalon.setOnClickListener {
            val salonName = binding.editSalonName.text.toString()
            val salonPhone = binding.editSalonPhone.text.toString().trim { it <= ' ' }
            val salonEmail = binding.editSalonEmail.text.toString().trim { it <= ' ' }
            val salonPassword = binding.editSalonPassword.text.toString().trim { it <= ' ' }

            //checa se o nome do salão foi colocado e notifica caso não tenha sido
            if (salonName.isEmpty()) {
                Toast.makeText(
                    this@SalonRegisterActivity,
                    POR_FAVOR_INSIRA_O_NOME_FANTASIA,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se o número de telefone do salão foi colocado e notifica caso não tenha sido
            if (salonPhone.isEmpty()) {
                Toast.makeText(
                    this@SalonRegisterActivity,
                    POR_FAVOR_INSIRA_O_NUMERO_TELEFONE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se o e-mail foi colocado e notifica caso não tenha sido
            if (salonEmail.isEmpty()) {
                Toast.makeText(
                    this@SalonRegisterActivity,
                    POR_FAVOR_INSIRA_SEU_EMAIL,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a senha foi colocada e notifica caso não tenha sido
            if (salonPassword.isEmpty()) {
                Toast.makeText(
                    this@SalonRegisterActivity,
                    POR_FAVOR_INSIRA_SUA_SENHA,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //cria o salão no firebase auth(autenticação)
            firebaseAuth
                .createUserWithEmailAndPassword(salonEmail, salonPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // captura numa variável o uid gerado pelo FirebaseAuth
                        val uid = firebaseAuth.currentUser?.uid

                        // Salvar no Firestore o salão
                        // Instancia um SalonModel
                        val salonModel = SalonModel(
                            firebaseUID = uid!!,
                            name = salonName,
                            phone = salonPhone,
                            email = salonEmail
                        )

                        // Grava o SalonModel no DB Salon do FireStore
                        firebaseDB
                            .collection(FIRESTORE_SALON_DB)
                            .document(uid)
                            .set(salonModel)
                            .addOnCompleteListener {
                                //se o cadastro for completado com sucesso
                                Toast.makeText(
                                    this@SalonRegisterActivity,
                                    CADASTRO_REALIZADO_COM_SUCESSO,
                                    Toast.LENGTH_SHORT
                                ).show()

                                // redireciona para a home
                                val intent =
                                    Intent(
                                        this@SalonRegisterActivity,
                                        SalonHomeActivity::class.java
                                    )
                                startActivity(intent)
                                finish()
                            }
                    } else {
                        //se o cadastro não for completado com sucesso
                        Toast.makeText(
                            this@SalonRegisterActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
