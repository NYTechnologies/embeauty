package com.nytech.embeauty.view.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nytech.embeauty.constants.ToastTextConstants.LOGIN_REALIZADO_COM_SUCESSO
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SEU_EMAIL
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SUA_SENHA
import com.nytech.embeauty.databinding.ActivityClientLoginBinding


/**
 * Tela de Login para cliente
 */
class ClientLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientLoginBinding
    private lateinit var firebase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientLoginBinding.inflate(layoutInflater)
        firebase = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        setContentView(binding.root)


        //botão para tela de cadastro de cliente
        binding.textSignupClient.setOnClickListener {
            val intent = Intent(this@ClientLoginActivity, ClientRegisterActivity::class.java)
            startActivity(intent)
        }

        //botão de login do cliente
        binding.buttonLoginClient.setOnClickListener {
            //armazenam os dados digitados nos campos e-mail e senha pelo cliente
            val email = binding.editClientEmail.text.toString().trim { it <= ' ' }
            val password = binding.editClientPassword.text.toString().trim { it <= ' ' }

            //checa se o e-mail foi colocado e notifica caso não tenha sido
            if (email.isEmpty()) {
                Toast.makeText(
                    this@ClientLoginActivity,
                    POR_FAVOR_INSIRA_SEU_EMAIL,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a senha foi colocada e notifica caso não tenha sido
            if (password.isEmpty()) {
                Toast.makeText(
                    this@ClientLoginActivity,
                    POR_FAVOR_INSIRA_SUA_SENHA,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //loga e autentica o cliente no firebase
            firebase
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@ClientLoginActivity,
                            LOGIN_REALIZADO_COM_SUCESSO,
                            Toast.LENGTH_SHORT
                        ).show()
                        //onde estou (@ClientLoginActivity) e para onde vou (ClientHomeActivity)... direciona o usuário p a próxima tela (home)
                        val intent =
                            Intent(this@ClientLoginActivity, ClientHomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        //se o login não for completado com sucesso
                        Toast.makeText(
                            this@ClientLoginActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
