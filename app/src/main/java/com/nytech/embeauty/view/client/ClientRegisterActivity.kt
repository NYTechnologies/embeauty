package com.nytech.embeauty.view.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nytech.embeauty.constants.ToastTextConstants.CADASTRO_REALIZADO_COM_SUCESSO
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SEU_EMAIL
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SUA_SENHA
import com.nytech.embeauty.databinding.ActivityClientRegisterBinding

/**
 * Tela de Cadastro para cliente
 */
class ClientRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientRegisterBinding
    private lateinit var firebase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientRegisterBinding.inflate(layoutInflater)
        firebase = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        setContentView(binding.root)

        // botão para tela de login do cliente
        binding.textLoginClient.setOnClickListener {
            onBackPressed()
        }

        // lógica de cadastro do novo cliente no Firebase
        binding.buttonRegisterClient.setOnClickListener {
            //armazenam os dados digitados nos campos e-mail e senha pelo cliente
            val email = binding.editClientEmail.text.toString().trim { it <= ' ' }
            val password = binding.editClientPassword.text.toString().trim { it <= ' ' }

            //checa se o e-mail foi colocado e notifica caso não tenha sido
            if (email.isEmpty()) {
                Toast.makeText(
                    this@ClientRegisterActivity,
                    POR_FAVOR_INSIRA_SEU_EMAIL,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a senha foi colocada e notifica caso não tenha sido
            if (password.isEmpty()) {
                Toast.makeText(
                    this@ClientRegisterActivity,
                    POR_FAVOR_INSIRA_SUA_SENHA,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //cria o cliente no firebase auth(autenticação)
            firebase
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //cliente cadastrado no firebase
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        //se o cadastro for completado com sucesso
                        Toast.makeText(
                            this@ClientRegisterActivity,
                            CADASTRO_REALIZADO_COM_SUCESSO,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //se o cadastro não for completado com sucesso
                        Toast.makeText(
                            this@ClientRegisterActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
